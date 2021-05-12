package com.onyemowo.diary.controllers;

import com.onyemowo.diary.exceptions.DiaryException;
import com.onyemowo.diary.exceptions.EntryException;
import com.onyemowo.diary.models.Entry;
import com.onyemowo.diary.payload.requestPayload.DiaryDTO;
import com.onyemowo.diary.payload.requestPayload.EntryDTO;
import com.onyemowo.diary.payload.responsePayload.ApiResponse;
import com.onyemowo.diary.service.DiaryService;
import com.onyemowo.diary.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diary")
public class DiaryController {
    private DiaryService diaryService;
    private EntryService entryService;

    @Autowired
    public DiaryController(DiaryService diaryService, EntryService entryService) {
        this.diaryService = diaryService;
        this.entryService = entryService;
    }

    @PostMapping("newDiary")
    public ResponseEntity<?> createDiary(@RequestBody DiaryDTO diaryDTO) {
        try {
            diaryService.createDiary(diaryDTO);
            ApiResponse newDiaryResponse = new ApiResponse(true, "New diary successfully created.");
            return new ResponseEntity<>(newDiaryResponse, HttpStatus.CREATED);
        } catch (DiaryException diaryException) {
            ApiResponse apiResponse = new ApiResponse(false, diaryException.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("findDiary")
    public ResponseEntity<?> findDiaryByName(@RequestParam(name = "dairyName") String name) {
        try {
            DiaryDTO foundDiary = diaryService.findDiaryByName(name);
            return new ResponseEntity<>(foundDiary, HttpStatus.FOUND);
        } catch (DiaryException diaryException) {
            ApiResponse response = new ApiResponse(false, diaryException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("deleteDiary")
    public ResponseEntity<?> deleteDiary(@RequestParam("dairyName") String name) {
        try {
            diaryService.deleteDiary(name);
            ApiResponse apiResponse = new ApiResponse(true, "Diary successfully deleted.");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (DiaryException diaryException) {
            ApiResponse response = new ApiResponse(false, diaryException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("UpdateDiary")
    public ResponseEntity<?> editDiary(@RequestParam(name = "dairyName") String name, @RequestBody DiaryDTO updatedDetails) {
        try {
            DiaryDTO diaryDTO = diaryService.editDiary(name, updatedDetails);
            return new ResponseEntity<>(diaryDTO, HttpStatus.OK);
        } catch (DiaryException diaryException) {
            ApiResponse apiResponse = new ApiResponse(false, diaryException.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("addNewEntry")
    public ResponseEntity<?> addNewEntry(@RequestParam("dairyId") int diaryId, @RequestBody EntryDTO newEntry) {
        try {
            diaryService.addNewEntry(diaryId, newEntry);
            ApiResponse apiResponse = new ApiResponse(true, "New entry successfully added.");
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (DiaryException diaryException) {
            ApiResponse response = new ApiResponse(false, diaryException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("deleteEntry")
    public ResponseEntity<?> deleteEntry(@RequestParam("dairyId") int diaryId, @RequestParam("entryId") long entryId) {
        try {
            diaryService.deleteEntry(diaryId, entryId);
            ApiResponse apiResponse = new ApiResponse(true, "Entry successfully deleted.");
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } catch (DiaryException | EntryException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("allEntries")
    public ResponseEntity<?> getAllEntries(@RequestParam("dairyId") int diaryId){
        try {
            List<EntryDTO> entryDTOList = diaryService.getAllEntries(diaryId);
            return new ResponseEntity<>(entryDTOList, HttpStatus.OK);
        }catch (DiaryException diaryException){
            ApiResponse response = new ApiResponse(false,diaryException.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("anEntry")
    public ResponseEntity<?> getAnEntry(@RequestParam("dairyId") int diaryId, @RequestParam("entryId") long entryId){
        try {
            EntryDTO entryDTO = diaryService.getAnEntry(diaryId,entryId);
            return new ResponseEntity<>(entryDTO, HttpStatus.OK);
        }catch (DiaryException | EntryException exception){
            ApiResponse response = new ApiResponse(false,exception.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

    }
    @PatchMapping("updateEntry")
    public ResponseEntity<?> editEntry(@RequestParam("dairyId") int diaryId, @RequestParam("entryId") long entryId, EntryDTO updateDetails){
        try {
            diaryService.editEntry(diaryId,entryId,updateDetails);
            ApiResponse apiResponse = new ApiResponse(true,"Entry updated successfully.");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch (DiaryException | EntryException exception){
            ApiResponse response = new ApiResponse(false,exception.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

    }

}
