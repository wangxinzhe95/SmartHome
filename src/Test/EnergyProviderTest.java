package Test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Controller.EnergyProvider;

public class EnergyProviderTest {
	@Test
	public void testCheckExisted() {
		boolean flag = EnergyProvider.checkUserExisted("55");
		assertEquals(true,flag);
	}
	
	@Test
	public void testCheckPassword() {
		boolean flag = EnergyProvider.checkUserPassword("55", "123");
		assertEquals(true, flag);
	}
	
	@Test
	public void testCountUsers() {
		int numbers = EnergyProvider.countUsers();
		assertEquals(7, numbers);
	}
	
	@Test
	public void testRemoveUser() {
		boolean flag1 = EnergyProvider.removeUser("951");
		boolean flag2 = EnergyProvider.checkUserExisted("951");
		assertEquals(flag1, flag2);
	}
	
}
