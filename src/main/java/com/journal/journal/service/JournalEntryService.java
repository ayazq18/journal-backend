package com.journal.journal.service;

import com.journal.journal.entity.JournalEntry;
import com.journal.journal.entity.User;
import com.journal.journal.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository JournalEntryRepository;

    @Autowired
    private UserService UserService;

    public void saveEntry(JournalEntry JournalEntry, String userName){
        try {
            User user = UserService.findByUserName(userName);
            JournalEntry saved = JournalEntryRepository.save(JournalEntry);
            user.getJournalEntries().add(saved);
            UserService.saveUser(user);
        }catch(Exception e){
            throw new RuntimeException("Something went wrong", e);
        }
    }

    public void saveEntry(JournalEntry JournalEntry){
        JournalEntryRepository.save(JournalEntry);
    }

    public List<JournalEntry> getAll(){
       return JournalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(Long id){
        return JournalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(String userName, Long id){
        boolean removed = false;
        try{
        User user = UserService.findByUserName(userName);
        removed = user.getJournalEntries().removeIf(x -> x.getId() == id);
           if(removed) {
               UserService.saveUser(user);
               JournalEntryRepository.deleteById(id);
           }
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("Error occured while deleting");
        }
        return removed;
    }
}
