/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.hybris.training.service;

public interface ParentExtensionService
{
	String getHybrisLogoUrl(String logoCode);

	void createLogo(String logoCode);
}
