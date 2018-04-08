package org.funfactorium.funfacts.topics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopicServiceTest {

    private static TopicService testService;
    private static TopicRepository mockRepository;
    private static List<Topic> mockTopicList;
    private static Topic mockTopic1;
    private static Topic mockTopic2;

    @BeforeAll
    public static void setUp() {
        mockRepository = mock(TopicRepository.class);
        mockTopicList = new ArrayList<>();
        mockTopic1 = mock(Topic.class);
        mockTopic2 = mock(Topic.class);
        mockTopicList.add(mockTopic1);
        mockTopicList.add(mockTopic2);
        testService = new TopicService(mockRepository);
    }

    @Test
    public void testFindAllWorksCorrectly() {
        when(mockRepository.findAll()).thenReturn(mockTopicList);
        List<Topic> testList = testService.allTopics();
        assertEquals(2, testList.size());
    }

    @Test
    public void testFindAllCanReturnEmptyList() {
        when(mockRepository.findAll()).thenReturn(new ArrayList<>());
        List<Topic> testList = testService.allTopics();
        assertEquals(0, testList.size());
    }

    @Test
    public void testGetTopicMapWorksCorrectly() {
        when(mockTopic1.getId()).thenReturn(1L);
        when(mockTopic1.getName()).thenReturn("test");
        when(mockTopic2.getId()).thenReturn(2L);
        when(mockTopic2.getName()).thenReturn("test");
        when(mockRepository.findAll()).thenReturn(mockTopicList);
        Map<Long, String> testMap = testService.getTopicMap();
        Set<Long> expectedSet = new HashSet<>(Arrays.asList(1L, 2L));
        assertEquals(expectedSet, testMap.keySet());
    }

    @Test
    public void testGetTopicMapWorksCanReturnEmptyMap() {
        when(mockRepository.findAll()).thenReturn(new ArrayList<>());
        Map<Long, String> testMap = testService.getTopicMap();
        assertEquals(0, testMap.keySet().size());
    }

}