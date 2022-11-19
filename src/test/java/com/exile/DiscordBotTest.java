package com.exile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DiscordBotTest 
{
    @Test
    public void shouldAnswerWithTrue()
    {
        List mockedList = mock(List.class);

        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();

        when(mockedList.get(0)).thenReturn("hello");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        assertEquals("hello", mockedList.get(0));
        assertThrows(RuntimeException.class, () -> mockedList.get(1));
    }
}
