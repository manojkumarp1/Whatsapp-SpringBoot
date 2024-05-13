package com.whatsapp.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Chat {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String chatName;
	private String chatImage;
	
	@ManyToMany
	private Set<User> admins = new HashSet<>();
	
	@Column(name="is_group")
	private boolean isGroup;
	
	@JoinColumn(name="create_by")
	@ManyToOne
	private User createdBy;
	
	@ManyToMany
	private Set<User> users = new HashSet<>();
	
	@OneToMany
	private List<Message> messages = new ArrayList<>();
	
	@Override
	public int hashCode() {
		return Objects.hash(chatImage, chatName, createdBy, id, isGroup, messages, users);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chat other = (Chat) obj;
		return Objects.equals(chatImage, other.chatImage) && Objects.equals(chatName, other.chatName)
				&& Objects.equals(createdBy, other.createdBy) && Objects.equals(id, other.id)
				&& isGroup == other.isGroup && Objects.equals(messages, other.messages)
				&& Objects.equals(users, other.users);
	}


}
