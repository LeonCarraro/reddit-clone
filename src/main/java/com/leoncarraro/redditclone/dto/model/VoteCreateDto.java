package com.leoncarraro.redditclone.dto.model;

import com.leoncarraro.redditclone.model.VoteType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class VoteCreateDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private VoteType voteType;
    private Long postId;

}
