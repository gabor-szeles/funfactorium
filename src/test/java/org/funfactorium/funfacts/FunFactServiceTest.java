package org.funfactorium.funfacts;

import org.funfactorium.funfacts.topics.Topic;
import org.funfactorium.funfacts.topics.TopicNotFoundException;
import org.funfactorium.funfacts.topics.TopicService;
import org.funfactorium.user.User;
import org.funfactorium.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FunFactServiceTest {

    private static FunFactService testService;
    private static TopicService mockTopicService;
    private static UserService mockUserService;
    private static FunFactRepository mockRepository;
    private static List<FunFact> mockFunFactList;
    private static FunFact mockFunFact;
    private static User mockUser;
    private static Topic mockTopic;
    private static Set<Topic> mockTopicSet;
    private static FunFactDto mockDto;

    @BeforeAll
    public static void setUpMocks() {
        mockTopicService = mock(TopicService.class);
        mockUserService = mock(UserService.class);
        mockTopic = mock(Topic.class);
        mockTopicSet = new HashSet<>();
        mockTopicSet.add(mockTopic);
        mockRepository = mock(FunFactRepository.class);
        mockFunFactList = new ArrayList<>();
        mockFunFact = mock(FunFact.class);
        mockFunFactList.add(mockFunFact);
        mockUser = mock(User.class);
        testService = new FunFactService(mockRepository, mockTopicService, mockUserService);
        mockDto = mock(FunFactDto.class);
    }

    private void setUpMockReturns() {
        when(mockFunFact.getTitle()).thenReturn("test");
        when(mockFunFact.getId()).thenReturn(1L);
        when(mockFunFact.getDescription()).thenReturn("test");
        when(mockFunFact.getAuthor()).thenReturn(mockUser);
        when(mockFunFact.getTopic()).thenReturn(mockTopicSet);
        when(mockFunFact.getRating()).thenReturn(3.0);
        when(mockTopic.getName()).thenReturn("test");
        when(mockUser.getUserName()).thenReturn("test");
        when(mockRepository.findByTopicSetId(any(Long.class))).thenReturn(mockFunFactList);
    }

    @Test
    public void testAllFunFactsWorksCorrectly() {
        when(mockRepository.findAll()).thenReturn(mockFunFactList);
        List<FunFact> testList = testService.allFunFacts();
        assertEquals(1, testList.size());
    }

    @Test
    public void testAllFunFactsCanReturnEmptyList() {
        when(mockRepository.findAll()).thenReturn(new ArrayList<>());
        List<FunFact> testList = testService.allFunFacts();
        assertEquals(0, testList.size());
    }

    @Test
    public void testSearchByTopicWorksCorrectly() {
        setUpMockReturns();
        List<Map<String, String>> testList = testService.searchByTopic(1);
        assertEquals(1, testList.size());
    }

    @Test
    public void testSearchByTopicReturnsAllForIdZero() {
        setUpMockReturns();
        when(mockRepository.findAll()).thenReturn(mockFunFactList);
        List<Map<String, String>> testList = testService.searchByTopic(0);
        assertEquals(1, testList.size());
    }

    @Test
    public void testGetRandomFunFactIdWorksCorrectly() {
        when(mockRepository.findMaxId()).thenReturn(10L);
        Set<Long> testSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            testSet.add(testService.getRandomFunFactId());
        }
        assertTrue(testSet.size()>1);
    }

    @Test
    public void testGetRandomFunFactIdThrowsException() {
        when(mockRepository.findMaxId()).thenReturn(null);
        assertThrows(NullPointerException.class, ()->testService.getRandomFunFactId());
    }

    @Test
    public void testGetFunFactWorksCorrectly() {
        setUpMockReturns();
        when(mockRepository.findById(any(Long.class))).thenReturn(mockFunFact);
        Map<String, Object> testMap = testService.getFunFact(1);
        assertEquals(7, testMap.keySet().size());
    }

    @Test
    public void testGetFunFactThrowsException() {
        when(mockRepository.findById(any(Long.class))).thenReturn(null);
        assertThrows(FunFactNotFoundException.class, ()->testService.getFunFact(1));
    }

    @Test
    public void testGetFunFactByTopicNameWorksCorrectly() {
        setUpMockReturns();
        when(mockRepository.findAllByTopicSet_name(any(String.class))).thenReturn(mockFunFactList);
        List<Map<String, Object>> testList = testService.getFunFactByTopicName("test");
        assertEquals("test", testList.get(0).get("title"));
    }

    @Test
    public void testGetFunFactByTopicNameThrowsException() {
        when(mockRepository.findAllByTopicSet_name(any(String.class))).thenReturn(new ArrayList<>());
        assertThrows(TopicNotFoundException.class, ()->testService.getFunFactByTopicName("test"));
    }

    @Test
    public void testGetFunFactByTopicIdWorksCorrectly() {
        setUpMockReturns();
        List<Map<String, Object>> testList = testService.getFunFactByTopicId(1);
        assertEquals("test", testList.get(0).get("title"));
    }

    @Test
    public void testGetFunFactByTopicIdThrowsException() {
        when(mockRepository.findByTopicSetId(any(Long.class))).thenReturn(new ArrayList<>());
        assertThrows(TopicNotFoundException.class, ()->testService.getFunFactByTopicId(1));
    }

    @Test
    public void testTitleExistsReturnsTrue() {
        when(mockRepository.existsByTitle(any(String.class))).thenReturn(true) ;
        assertTrue(testService.titleExists("test"));
    }

    @Test
    public void testTitleExistsReturnsFalse() {
        when(mockRepository.existsByTitle(any(String.class))).thenReturn(false) ;
        assertFalse(testService.titleExists("test"));
    }
    


}