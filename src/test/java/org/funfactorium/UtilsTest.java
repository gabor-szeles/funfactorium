package org.funfactorium;

import org.funfactorium.funfacts.FunFact;
import org.funfactorium.funfacts.topics.Topic;
import org.funfactorium.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UtilsTest {

    private static List<FunFact> mockFunFactList;
    private static FunFact mockFunFact;
    private static User mockUser;
    private static Topic mockTopic;
    private static Set<Topic> mockTopicSet;

    @BeforeAll
    public static void setUp() {
        mockFunFact = mock(FunFact.class);
        mockFunFactList = new ArrayList<>();
        mockFunFactList.add(mockFunFact);
        mockUser = mock(User.class);
        mockTopic = mock(Topic.class);
        mockTopicSet = new HashSet<>();
        mockTopicSet.add(mockTopic);
        setUpMockReturns();
    }

    private static void setUpMockReturns() {
        when(mockFunFact.getTitle()).thenReturn("test");
        when(mockFunFact.getId()).thenReturn(1L);
        when(mockFunFact.getDescription()).thenReturn("test");
        when(mockFunFact.getAuthor()).thenReturn(mockUser);
        when(mockFunFact.getTopic()).thenReturn(mockTopicSet);
        when(mockFunFact.getRating()).thenReturn(3.0);
        when(mockTopic.getName()).thenReturn("test");
        when(mockUser.getUserName()).thenReturn("test");
    }

    @Test
    public void testBuildFilteredPageWorksCorrectly() {
        List<Map<String, String>> testList = Utils.buildFilteredPage(mockFunFactList);
        assertEquals("test", testList.get(0).get("description"));
    }

    @Test
    public void testBuildFilteredPageThrowsNullPointerException() {
        assertThrows(NullPointerException.class, ()-> Utils.buildFilteredPage(null));
    }

    @Test
    public void testBuildSingleFactJsonWorksCorrectly() {
        Map<String, Object> testMap = Utils.buildSingleFactJson(mockFunFact);
        assertEquals(3.0, testMap.get("rating"));
    }

    @Test
    public void testBuildFactListJsonWorksCorrectly() {
        List<Map<String, Object>> testList = Utils.buildFactListJson(mockFunFactList);
        assertEquals(3.0, testList.get(0).get("rating"));
    }

    // You should never get to this happening. If the build methods throw an exception something
    // is off in the FunFactService class methods using the Utils methods.
    // Hence, please keep letting these methods throw a NullPointerException
    @Test
    public void testBuildSingleFactJsonThrowsException() {
        assertThrows(NullPointerException.class, ()-> Utils.buildSingleFactJson(null));
    }

    @Test
    public void testBuildFactListJsonThrowsException() {
        assertThrows(NullPointerException.class, ()-> Utils.buildFactListJson(null));
    }

    @ParameterizedTest
    @CsvSource({"topic, No such topic was found in the database!",
                "funfact, No entry with this id was found in the database!",
                "null, Database empty!"})
    public void testBuildApiErrorMessageCatchesFunFactException(
                    @ConvertWith(CustomExceptionArgumentConverter.class) RuntimeException e,
                    String errorMsg) {
        Map<String, String> testMap = Utils.buildApiErrorMessage(e);
        assertEquals(errorMsg, testMap.get("description"));
    }




}