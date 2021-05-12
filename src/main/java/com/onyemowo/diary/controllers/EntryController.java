package com.onyemowo.diary.controllers;

import com.onyemowo.diary.exceptions.EntryException;
import com.onyemowo.diary.models.Entry;
import com.onyemowo.diary.payload.requestPayload.EntryDTO;
import com.onyemowo.diary.payload.responsePayload.ApiResponse;
import com.onyemowo.diary.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/entry/")
public class EntryController {
    private final EntryService entryService;

    @Autowired
    public EntryController(EntryService entryService){
        this.entryService = entryService;
    }

    @GetMapping("entries")
    public ResponseEntity<?> findAllEntries(){
        List<EntryDTO> dto = entryService.getAllEntries(0);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("entries/{page}")
    public ResponseEntity<?> findAllEntries(@PathVariable String page){
        int pageNo = Integer.parseInt(page);
        List<EntryDTO> dtos = entryService.getAllEntries(pageNo);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    @PostMapping("newEntry")
    public ResponseEntity<?> createNewEntry(@RequestBody EntryDTO entryDTO){
        entryService.createNewEntry(entryDTO);
        ApiResponse response = new ApiResponse(true, "Entry successfully created.");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @GetMapping("entryById")
    public ResponseEntity<?> findEntryById(@RequestParam("id") String id ){
        try {
            long entryId = Long.parseLong(id);
            EntryDTO dto = entryService.getEntryById(entryId);
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }catch (EntryException entryException){
            ApiResponse exceptionResponse = new ApiResponse(false, entryException.getMessage());
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("deleteEntry")
    public ResponseEntity<?> deleteEntry(@RequestParam("id") String id ){
        try {
            long entryId = Long.parseLong(id);
            entryService.deleteEntryById(entryId);
            ApiResponse deleteResponse = new ApiResponse(true,"Entry successfully deleted");
            return new ResponseEntity<>(deleteResponse,HttpStatus.NO_CONTENT);
        }catch (EntryException noEntryException){
            ApiResponse apiResponse = new ApiResponse(false, noEntryException.getMessage());
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("updateEntry")
    public ResponseEntity<?> updateEntry(@RequestParam("id") String id, @RequestBody EntryDTO entryDTO){
        try {
            long entryId = Long.parseLong(id);
            entryService.editEntry(entryDTO,entryId);
            ApiResponse apiResponse = new ApiResponse(true,"Entry successfully updated.");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch (EntryException entryException){
            ApiResponse apiResponse = new ApiResponse(false,entryException.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("phraseSearch")
        public ResponseEntity<?> searchByPhrase(@RequestParam(name = "pageNumber", required = false) Optional<Integer> pageNumber, @RequestParam("phrase") String phrase){
        try {
            int pageNo = pageNumber.orElse(0);
            List<EntryDTO> entryDTOS = entryService.entryBodySearch(phrase,pageNo);
            return new ResponseEntity<>(entryDTOS, HttpStatus.OK);
        }catch (EntryException entryException){
            ApiResponse apiResponse = new ApiResponse(false,entryException.getMessage());

            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

    }


}
