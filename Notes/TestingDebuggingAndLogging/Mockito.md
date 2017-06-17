# Mockito #

- http://site.mockito.org/

Mockito is a mocking framework.

```java
import org.mockito.Mockito.when;

public void mockito() {

	// given
    RealClass stubRealClass = mock(RealClass.class);
    when(stubRealClass.loadData()).thenReturn(exampleData);

    RealClassService realClassService = new RealClassService(stubRealClass);

    // when
    int result = realClassService.doSomething();

    // then
    assertEquals("Calculated wrong result", expectedResult, result);
    verify(stubRealClass, times(1)).doSomething();
    verify(mockOutput).anotherSomething(anyString(), eq(1), eq("VALUE"));
}
```