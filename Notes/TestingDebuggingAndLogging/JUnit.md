#JUnit#

- https://developer.android.com/topic/libraries/testing-support-library/index.html#ajur-junit

Junit is a testing framework; this was then ported to .NET as NUnit.

- http://junit.org/junit4/

```java
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MyTest {
    @Test
    public void canDoX() {
        assertEquals(expectedResult, result);
        assertTrue(isFoo);
        assertFalse(isFoo);
        assertEquals(“Test wrong message”, 1, result.size());
    }
}
```

##Hamcrest##

- http://hamcrest.org/JavaHamcrest/
- https://code.google.com/archive/p/hamcrest/wikis/Tutorial.wiki

```java
assertThat(values, hasKey("B"));

assertThat(numbers, hasItem(5));
	assertThat(referenceType, hasProperty("FieldName", equalTo("ExpectedFieldNameData")));
        
assertThat (referenceType, hasItem(allOf(
    hasProperty("A", equalTo("a")),
    hasProperty("B", equalTo(1)),
    hasProperty("C", equalTo("C")))));
```



##Expecting Exceptions##

```java
@Test(expected = IllegalArgumentException.class)
public void doSomething() {
}
```

##Test Fixture/Test Setup/Tear Down##

We can annotate our test and test fixture setup and tear down methods with the following attributes.

- @Before
	- Before each test method runs
- @After
	- After each test method runs
- @BeforeClass
	- Before all tests in the class
- @AfterClass
	- After all tests in the class


