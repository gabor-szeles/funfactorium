package org.funfactorium.funfacts;

import org.funfactorium.funfacts.topics.Topic;
import org.funfactorium.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FunFactServiceTest {

    private static FunFactService testService;
    private static FunFactRepository mockRepository;
    private static List<FunFact> mockFunFactList;
    private static FunFact mockFunFact;
    private static User mockUser;
    private static Topic mockTopic;
    private static Set<Topic> mockTopicSet;

    @BeforeAll
    public static void setUpMocks() {
        mockTopic = mock(Topic.class);
        mockTopicSet = new HashSet<>();
        mockTopicSet.add(mockTopic);
        mockRepository = mock(FunFactRepository.class);
        mockFunFactList = new ArrayList<>();
        mockFunFact = mock(FunFact.class);
        mockFunFactList.add(mockFunFact);
        mockUser = mock(User.class);
        testService = new FunFactService(mockRepository);
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


}