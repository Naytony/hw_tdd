package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

public class PhoneBookTest {
    private PhoneBook phoneBook;

    @BeforeEach
    public void fillPhoneBook() {
        phoneBook = new PhoneBook();
        phoneBook.phoneBook.put("Rick", "+70000000000");
        phoneBook.phoneBook.put("Todd", "+77777777777");
        phoneBook.phoneBook.put("Kim", "+79991111111");
    }

    @ParameterizedTest
    @MethodSource("parametersMethodTestAdd")
    public void testAdd(String name, String phoneNumber, int expected) {
        //Act
        int executable = phoneBook.add(name, phoneNumber);

        //Assert
        Assertions.assertEquals(expected, executable);
    }

    @ParameterizedTest
    @MethodSource("parametersMethodTestFindByNumber")
    public void testFindByNumber(String phoneNumber, String expected) {
        //Act
        String executable = phoneBook.findByNumber(phoneNumber);

        //Assert
        Assertions.assertEquals(expected, executable);
    }

    @ParameterizedTest
    @MethodSource("parametersMethodTestFindByName")
    public void testFindByName(String name, String expected) {
        //Act
        String executable = phoneBook.findByName(name);

        //Assert
        Assertions.assertEquals(expected, executable);
    }

    @Test
    public void testPrintAllNames() {
        //Arrange
        String expected = "Kim Rick Todd";
        String executable = "";

        //Act
        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(100);
            PrintStream capture = new PrintStream(outputStream);
            System.setOut(capture);
            phoneBook.printAllNames();
            capture.flush();
            executable = outputStream.toString();
            System.setOut(originalOut);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Assert
        Assertions.assertEquals(expected, executable);
    }

    //******* Parameters fo tests *******
    public static Stream<Arguments> parametersMethodTestAdd() {
        return Stream.of(
                Arguments.of("Kate", "+79998887766", 4),
                Arguments.of("Kim", "+71112223344", 3)
        );
    }

    public static Stream<Arguments> parametersMethodTestFindByNumber() {
        return Stream.of(
                Arguments.of("+79991111111", "Kim"),
                Arguments.of("+70000000000", "Rick"),
                Arguments.of("+75556667766", "ОШИБКА! Контакт не найден!"),
                Arguments.of("+77777777777", "Todd")
        );
    }

    public static Stream<Arguments> parametersMethodTestFindByName() {
        return Stream.of(
                Arguments.of("Kim", "+79991111111"),
                Arguments.of("Rick", "+70000000000"),
                Arguments.of("John", "ОШИБКА! Контакт не найден!"),
                Arguments.of("Todd", "+77777777777")
        );
    }
}
