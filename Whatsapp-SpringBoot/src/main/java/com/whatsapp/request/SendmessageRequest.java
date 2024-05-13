package com.whatsapp.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SendmessageRequest {
	private Integer userId;
	private Integer chatId;
	private String content;

}
