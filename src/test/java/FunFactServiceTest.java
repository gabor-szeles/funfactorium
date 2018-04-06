import org.funfactorium.funfacts.FunFact;
import org.funfactorium.funfacts.FunFactRepository;
import org.funfactorium.funfacts.FunFactService;
import org.funfactorium.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FunFactServiceTest {

    private static FunFactService testService;
    private static FunFactRepository mockRepository;
    private static List<FunFact> mockFunFactList;
    private static FunFact mockFunFact;
    private static User mockUser;

    @BeforeAll
    public static void setUpMocks() {
        mockRepository = mock(FunFactRepository.class);
        mockFunFactList = new ArrayList<>();
        mockFunFact = mock(FunFact.class);
        mockFunFactList.add(mockFunFact);
        mockUser = mock(User.class);
        testService = new FunFactService(mockRepository);
    }

    private void setUpMockReturns() {
        when(mockFunFact.getTitle()).thenReturn("test");
        when(mockFunFact.getDescription()).thenReturn("test");
        when(mockFunFact.getAuthor()).thenReturn(mockUser);
        when(mockUser.getUserName()).thenReturn("test");
        when(mockRepository.findByTopicSetId(any(Long.class))).thenReturn(mockFunFactList);
    }

    @Test
    public void testFindAllWorksCorrectly() {
        when(mockRepository.findAll()).thenReturn(mockFunFactList);
        List<FunFact> testList = testService.allFunFacts();
        assertEquals(1, testList.size());
    }

    @Test
    public void testFindAllCanReturnEmptyList() {
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


}