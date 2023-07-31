package com.login.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.login.demo.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

}
