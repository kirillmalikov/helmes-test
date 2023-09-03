package com.helmes.helmes_test.domain.user_sector.model;

import static jakarta.persistence.FetchType.LAZY;

import com.helmes.helmes_test.domain.sector.model.Sector;
import com.helmes.helmes_test.domain.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserSector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sector_id", referencedColumnName = "id")
    private Sector sector;
}
