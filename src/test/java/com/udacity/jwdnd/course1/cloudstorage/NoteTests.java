package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Notes tests.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTests extends CloudStorageApplicationTests {

    @Test
    public void testDelete() {
        String noteTitle = "First note";
        String noteDescription = "This is my first note...";
        HomePage homePage = signUpAndLogin();
        createNote(noteTitle, noteDescription, homePage);
        homePage.navToNotesTab();
        homePage = new HomePage(driver);
        Assertions.assertFalse(homePage.noNotes(driver));
        deleteNote(homePage);
        Assertions.assertTrue(homePage.noNotes(driver));
    }

    private void deleteNote(HomePage homePage) {
        homePage.deleteNote();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
    }

    @Test
    public void testCreateAndDisplay() {
        String noteTitle = "Note XYZ";
        String noteDescription = "Lorem ipsum ....";
        HomePage homePage = signUpAndLogin();
        createNote(noteTitle, noteDescription, homePage);
        homePage.navToNotesTab();
        homePage = new HomePage(driver);
        Note note = homePage.getFirstNote();
        Assertions.assertEquals(noteTitle, note.getNoteTitle());
        Assertions.assertEquals(noteDescription, note.getNoteDescription());
        deleteNote(homePage);
        homePage.logout();
    }

    @Test
    public void testModify() {
        String noteTitle = "Test title";
        String noteDescription = "Lorem ipsum dolor....";
        HomePage homePage = signUpAndLogin();
        createNote(noteTitle, noteDescription, homePage);
        homePage.navToNotesTab();
        homePage = new HomePage(driver);
        homePage.editNote();
        String modifiedNoteTitle = "Test title 2";
        homePage.modifyNoteTitle(modifiedNoteTitle);
        String modifiedNoteDescription = "Lorem ipsum dolor, lorem ipsum dolor....";
        homePage.modifyNoteDescription(modifiedNoteDescription);
        homePage.saveNoteChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToNotesTab();
        Note note = homePage.getFirstNote();
        Assertions.assertEquals(modifiedNoteTitle, note.getNoteTitle());
        Assertions.assertEquals(modifiedNoteDescription, note.getNoteDescription());
    }

    private void createNote(String noteTitle, String noteDescription, HomePage homePage) {
        homePage.navToNotesTab();
        homePage.addNewNote();
        homePage.setNoteTitle(noteTitle);
        homePage.setNoteDescription(noteDescription);
        homePage.saveNoteChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToNotesTab();
    }
}
