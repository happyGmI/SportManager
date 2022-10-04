package com.w.sportmanager.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SportSignHistory {

    @Id
    private String id;

    private Integer signTimes;

    private List<String> sportDate;

    private List<Integer> sportTime;

}
