package com.demo.security.service;

import com.demo.security.dto.make.MakeCreateDto;
import com.demo.security.dto.make.MakeResponseDto;
import com.demo.security.dto.make.MakeUpdateDto;
import com.demo.security.entity.Make;
import com.demo.security.exception.ProductAlreadyExistException;
import com.demo.security.exception.ResourceNotFoundException;
import com.demo.security.exception.ValidationException;
import com.demo.security.mapper.MakeMapper;
import com.demo.security.repository.MakeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MakeService {
    private final MakeRepository makeRepository;
    private final MakeMapper makeMapper;

    public List<MakeResponseDto> getAllMakes(String makeName) {
        List<Make> activeMakes;

        if (makeName != null && !makeName.isBlank()) {
            activeMakes = makeRepository.searchAllMakes(makeName);
        } else {
            activeMakes = makeRepository.findAll();
        }

        return activeMakes.stream()
                .map(makeMapper::toMakeResponseDto).toList();
    }

    public void deleteMake(UUID id) {
        Make make = makeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Make by " + id + " does not exist"));
        if (!make.getModels().isEmpty()) {
            throw new ValidationException("Make is connected to models");
        }
        makeRepository.deleteById(id);
    }

    public boolean createMake(MakeCreateDto makeCreateDto) {
        Optional<Make> byMakeName = makeRepository.findByMakeName(makeCreateDto.getMakeName());
        if (byMakeName.isPresent()) {
            throw new ProductAlreadyExistException(makeCreateDto.getMakeName() + " is already exist");
        }

        Make make = new Make(
                UUID.randomUUID(),
                makeCreateDto.getMakeName(),
                makeCreateDto.getIsActive(),
                null,
                null,
                null
        );

        makeRepository.save(make);

        return true;
    }

    public MakeResponseDto getSingleMake(UUID id) {
        Make byId = makeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Make by " + id + " does not exist"));
        return makeMapper.toMakeResponseDto(byId);
    }

    public MakeResponseDto updateMake(UUID id, MakeUpdateDto update) {
        // Fetch the Make object by ID, or throw an exception if not found
        Make byId = makeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Make by " + id + " does not exist"));

        // Check if a Make with the same name already exists, excluding the current one
        boolean exists = makeRepository.existsByMakeNameAndIdNot(update.getMakeName(), id);
        if (exists) {
            throw new ProductAlreadyExistException("Make with name " + update.getMakeName() + " already exists.");
        }
        // Proceed with updating the Make object
        byId.setMakeName(update.getMakeName());
        byId.setIsActive(update.getIsActive());

        // Save the updated entity
        makeRepository.save(byId);

        // Return the updated MakeResponseDto
        return makeMapper.toMakeResponseDto(byId);
    }


    public List<MakeResponseDto> getAllActiveMakes(String makeName) {
        List<Make> activeMakes;

        if (makeName != null && !makeName.isBlank()) {
            activeMakes = makeRepository.searchActiveMakes(makeName);
        } else {
            activeMakes = makeRepository.findByIsActiveTrue();
        }

        return activeMakes.stream()
                .map(makeMapper::toMakeResponseDto).toList();
    }

    public List<MakeResponseDto> getAllNonActiveMakes(String makeName) {
        List<Make> activeMakes;

        if (makeName != null && !makeName.isBlank()) {
            activeMakes = makeRepository.searchNonActiveMakes(makeName);
        } else {
            activeMakes = makeRepository.findByIsActiveFalse();
        }

        return activeMakes.stream()
                .map(makeMapper::toMakeResponseDto).toList();
    }
}

