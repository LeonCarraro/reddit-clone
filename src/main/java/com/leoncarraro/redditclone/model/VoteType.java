package com.leoncarraro.redditclone.model;

public enum VoteType {

	UPVOTE(1),
	DOWNVOTE(-1);
	
	int direction;
	
	private VoteType(int direction) {
		this.direction = direction;
	}
	
}
