package org.byron4j.ssm_core.spring_core.xmlcfg.service;

import org.byron4j.ssm_core.spring_core.xmlcfg.dao.AccountDao;
import org.byron4j.ssm_core.spring_core.xmlcfg.dao.ItemDao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PetStoreServiceImpl implements PetStoreService{
	private AccountDao accountDao;
	private ItemDao itemDao;
}
