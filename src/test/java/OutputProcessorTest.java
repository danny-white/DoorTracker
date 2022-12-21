import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OutputProcessorTest {

    RedisFacade mockredis = Mockito.mock(RedisFacade.class);
    DoorSerializer mockSerde = Mockito.mock(DoorSerializer.class);
    DoorMatcher matcher = new DoorMatcher();
    private OutputProcessor op = new OutputProcessor(mockredis, mockSerde);


    ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);

    static LocalDateTime ldt = LocalDateTime.of(2022,12,12,12,12);

    @Test
    public void testOutput() {
        String item = "item";

        when(mockSerde.serialize(ArgumentMatchers.argThat(matcher))).thenReturn("serializedData");
        op.process(item, ldt);
        verify(mockredis).set(keyCaptor.capture(), valueCaptor.capture());

        assertTrue(keyCaptor.getAllValues().get(0).contains("Danewhi"));
        assertTrue(keyCaptor.getAllValues().get(0).contains("2022-12-12T12:12"));
        assertEquals("serializedData", valueCaptor.getAllValues().get(0));
    }

    private static class DoorMatcher implements ArgumentMatcher<DoorData> {
        @Override
        public boolean matches(DoorData doorData) {
            return doorData.getType().equals("item") && Math.abs(doorData.getTimestamp().until(ldt, ChronoUnit.MINUTES)) < 1;
        }
    }
}