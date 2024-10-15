package org.denis.coinkeeper.api.Services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.denis.coinkeeper.api.dto.ProfitDto;
import org.denis.coinkeeper.api.entities.ProfitEntity;
import org.denis.coinkeeper.api.entities.UserEntity;
import org.denis.coinkeeper.api.exceptions.BadRequestException;
import org.denis.coinkeeper.api.factories.ProfitDtoFactory;
import org.denis.coinkeeper.api.repositories.ProfitRepository;
import org.denis.coinkeeper.api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfitService {

    private final ProfitRepository profitRepository;
    private final ProfitDtoFactory profitDtoFactory;
    private final UserRepository userRepository;

    @Transactional
    public void createProfit(ProfitDto profitDto,String email) {

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            ProfitEntity profitEntity = ProfitEntity.builder()
                    .name(profitDto.getName())
                    .price(profitDto.getPrice())
                    .category(profitDto.getCategory())
                    .build();

            userEntity.addProfit(profitEntity);

            userRepository.save(userEntity);
        }
        else {
            throw new BadRequestException("This user not found");
        }

    }

    public List<ProfitDto> getProfits(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            List<ProfitEntity> profitEntityList = userEntity.getProfitList();

            return profitEntityList
                    .stream()
                    .map(profitDtoFactory::makeProfitDto)
                    .toList();
        }
        else {
            throw new BadRequestException("This user not found");
        }
    }
    public ProfitDto getProfitById(Long profitId,
                                   String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            ProfitEntity profitEntity = profitRepository.findProfitEntitiesByProfitIdAndUser(profitId, userEntityOptional.get());
            if (profitEntity != null) {
                return profitDtoFactory.makeProfitDto(profitEntity);
            } else {
                throw new BadRequestException("This id profit not found");
            }
        }
        else {
            throw new BadRequestException("This user not found");
        }

    }

    @Transactional
    public ProfitDto putProfit(Long profitId,
                               ProfitDto profitDto,
                               String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {

            UserEntity userEntity = userEntityOptional.get();

            ProfitEntity profitEntityOrigin = profitRepository.findProfitEntitiesByProfitIdAndUser(profitId,userEntity);

            ProfitEntity profitEntity = ProfitEntity.builder()
                    .name(profitDto.getName())
                    .category(profitDto.getCategory())
                    .price(profitDto.getPrice())
                    .AddedAt(profitDto.getAddedAt())
                    .build();

            if (profitEntityOrigin != null) {
                if (!profitEntityOrigin.getName().equals(profitEntity.getName())) {
                    profitEntityOrigin.setName(profitEntity.getName());
                }
                else if (!profitEntityOrigin.getCategory().equals(profitEntity.getCategory())) {
                    profitEntityOrigin.setCategory(profitEntity.getCategory());
                }
                else if (!profitEntityOrigin.getPrice().equals(profitEntity.getPrice())) {
                    profitEntityOrigin.setPrice(profitEntity.getPrice());
                }
                profitRepository.save(profitEntityOrigin);

                return profitDtoFactory.makeProfitDto(profitEntityOrigin);
            }
            else {
                throw new BadRequestException("This id profit not found");
            }
        }
        else {
            throw new BadRequestException("This user not found");
        }

    }
    @Transactional
    public void removeProfitById(Long profitId,
                                   String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();
            ProfitEntity profitEntity = profitRepository.findProfitEntitiesByProfitIdAndUser(profitId, user);
            if (profitEntity != null) {
                user.removeProfit(profitEntity);
            } else {
                throw new BadRequestException("This id profit not found");
            }
        }

    }

}
