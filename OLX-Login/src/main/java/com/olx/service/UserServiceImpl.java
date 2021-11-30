package com.olx.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.olx.entity.UserEntity;
import com.olx.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserEntity> userList = this.userRepo.findByUsername(username);
		if (userList.size() == 0) {
			throw new UsernameNotFoundException(username);
		}
		UserEntity userEntity = userList.get(0);
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(userEntity.getRoles()));
		User user = new User(userEntity.getUsername(), passwordEncoder.encode(userEntity.getPassword()),
				grantedAuthorities);
		return user;
	}

	@Override
	public UserEntity createNewUser(UserEntity user) {
		UserEntity userCreated = this.userRepo.save(user);
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(userCreated.getRoles()));
		// User user2 = new User(userCreated.getUsername(),
		// passwordEncoder.encode(userCreated.getPassword()), grantedAuthorities);//new
		// User(0, userEntity.getUserName(),
		// passwordEncoder.encode(userEntity.getPassword()), userEntity.getRoles());
		return userCreated;
	}

	@Override
	public UserEntity getUserDetails(String username) {
		List<UserEntity> userList = this.userRepo.findByUsername(username);
		if (userList.size() == 0) {
			throw new UsernameNotFoundException(username);
		}
		UserEntity userEntity = userList.get(0);
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(userEntity.getRoles()));
		User user = new User(userEntity.getUsername(), passwordEncoder.encode(userEntity.getPassword()),
				grantedAuthorities);
		return userEntity;
	}

	/*
	 * private BlackListDocument getBlackListDocumentFromDTO(Blacklist blacklist) {
	 * 
	 * BlackListDocument blackListDocument = this.modelMapper.map(blacklist,
	 * BlackListDocument.class);
	 * 
	 * return blackListDocument; }
	 * 
	 * private Blacklist getBlackListDTOFromDocument(BlackListDocument
	 * blackListDocument) {
	 * 
	 * Blacklist blacklist = this.modelMapper.map(blackListDocument,
	 * Blacklist.class); return blacklist; }
	 * 
	 * private List<Blacklist>
	 * getDTOListFromBlackListDocumentList(List<BlackListDocument>
	 * blackListDocuments) { List<Blacklist> blackListDtoList = new
	 * ArrayList<Blacklist>(); for (BlackListDocument blackListDocument :
	 * blackListDocuments) {
	 * blackListDtoList.add(getBlackListDTOFromDocument(blackListDocument)); }
	 * return blackListDtoList;
	 * 
	 * }
	 * 
	 * @Override public boolean insertBlackListUserId(String authToken) { Blacklist
	 * blacklist = new Blacklist(authToken);
	 * blackListMongoRepo.save(getBlackListDocumentFromDTO(blacklist)); return
	 * false; }
	 * 
	 * @Override public boolean checkBlackListUser(String authToken) {
	 * Optional<BlackListDocument> blackListDocument=
	 * blackListMongoRepo.findById(authToken); if (blackListDocument.isPresent()) {
	 * return true; } else return false; }
	 */
}
