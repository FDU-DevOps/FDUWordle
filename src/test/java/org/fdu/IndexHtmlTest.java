package org.fdu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class IndexHtmlTest
{
    private String htmlContent;

    @BeforeEach
    void setUp() throws Exception
    {
        htmlContent = Files.readString(
                Path.of("src/main/resources/static/index.html"));
    }

    @Test
    void testIndexPageContainsTitle()
    {
        assertTrue(htmlContent.contains("FDUWordle"));
    }

    @Test
    void testIndexPageContainsGuessInput()
    {
        assertTrue(htmlContent.contains("submit-guess"));
    }

    @Test
    void testIndexPageContainsSubmitButton()
    {
        assertTrue(htmlContent.contains("sendUserGuess"));
    }

    @Test
    void testIndexPageContainsResponseOutput()
    {
        assertTrue(htmlContent.contains("output"));
    }
}