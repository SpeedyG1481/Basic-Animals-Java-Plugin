package com.speedyg.ras.messages;

import java.io.IOException;

import com.speedyg.ras.BasicAnimals;

public class Messages {

	public static String change_owner_successfull;
	public static String error_money_not_enought;
	public static String only_your_mob;
	public static String just_sold_out;
	public static String not_permission;
	public static String just_player_command;
	public static String not_your_mob;
	public static String only_hired;
	public static String mob_sended_home;
	public static String hire_success;
	public static String change_price_1;
	public static String change_price_2;
	public static String change_price_3;
	public static String change_name_1;
	public static String change_name_2;
	public static String sell_status_updated;
	public static String wrong_usage;
	public static String user_is_not_online;
	public static String just_number_pls;
	public static String send_success;
	public static String reload_suceess;

	public static void loadMessages() {
		if (BasicAnimals.getInstance().getOptionsFile() != null
				&& BasicAnimals.getInstance().getOptionsFile().exists()) {
			if (!BasicAnimals.getInstance().getOptions().isSet("Buy_Succesfull"))
				BasicAnimals.getInstance().getOptions().set("Buy_Succesfull",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &aHayvanı başarıyla satın aldınız!"
								: "&8[&bBM&8] &aYou are bought mob successfully!");
			change_owner_successfull = BasicAnimals.getInstance().getOptions().getString("Buy_Succesfull")
					.replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Not_Enough_Money"))
				BasicAnimals.getInstance().getOptions().set("Not_Enough_Money",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &cBu hayvanı satın alacak paraya sahip değilsiniz!"
								: "&8[&bBM&8] &cYou haven't enough money for buy this mob!");
			error_money_not_enought = BasicAnimals.getInstance().getOptions().getString("Not_Enough_Money")
					.replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Only_Your_Mob"))
				BasicAnimals.getInstance().getOptions().set("Only_Your_Mob",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &cBu hayvanın sahibi zaten sensin!"
								: "&8[&bBM&8] &cYou're already the owner!");
			only_your_mob = BasicAnimals.getInstance().getOptions().getString("Only_Your_Mob").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Just_Sold"))
				BasicAnimals.getInstance().getOptions().set("Just_Sold",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &cBu hayvan satılmış!"
								: "&8[&bBM&8] &cMob just sold!");
			just_sold_out = BasicAnimals.getInstance().getOptions().getString("Just_Sold").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Enough_Perm"))
				BasicAnimals.getInstance().getOptions().set("Enough_Perm",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &cBunun için yetkin yok!"
								: "&8[&bBM&8] &cYou can't do this, you haven't permission!");
			not_permission = BasicAnimals.getInstance().getOptions().getString("Enough_Perm").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Only_Player_Command"))
				BasicAnimals.getInstance().getOptions().set("Only_Player_Command",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &cBu komut sadece oyun içerisinden geçerlidir!"
								: "&8[&bBM&8] &cCommand only use players!");
			just_player_command = BasicAnimals.getInstance().getOptions().getString("Only_Player_Command")
					.replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Not_Your_Mob"))
				BasicAnimals.getInstance().getOptions().set("Not_Your_Mob",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &cBu hayvanın sahibi siz değilsiniz!"
								: "&8[&bBM&8] &cYou are not owner of the mob!");
			not_your_mob = BasicAnimals.getInstance().getOptions().getString("Not_Your_Mob").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Only_Hired"))
				BasicAnimals.getInstance().getOptions().set("Only_Hired",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &cBu hayvanı zaten çağırmışsınız, yanınıza getirdik."
								: "&8[&bBM&8] &cMob already hired, teleported your location.");
			only_hired = BasicAnimals.getInstance().getOptions().getString("Only_Hired").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Sended_Home"))
				BasicAnimals.getInstance().getOptions().set("Sended_Home",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &aHayvanı evine gönderdiniz."
								: "&8[&bBM&8] &aMob sended home.");
			mob_sended_home = BasicAnimals.getInstance().getOptions().getString("Sended_Home").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Hire_Success"))
				BasicAnimals.getInstance().getOptions().set("Hire_Success",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &aHayvan çağırıldı!"
								: "&8[&bBM&8] &aMob hired!");
			hire_success = BasicAnimals.getInstance().getOptions().getString("Hire_Success").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Change_Price_1"))
				BasicAnimals.getInstance().getOptions().set("Change_Price_1",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR") ? "&0Lütfen yeni bir"
								: "&0Please input a");
			change_price_1 = BasicAnimals.getInstance().getOptions().getString("Change_Price_1").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Change_Price_2"))
				BasicAnimals.getInstance().getOptions().set("Change_Price_2",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR") ? "&0satış değeri girin"
								: "&0new price");
			change_price_2 = BasicAnimals.getInstance().getOptions().getString("Change_Price_2").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Change_Price_3"))
				BasicAnimals.getInstance().getOptions().set("Change_Price_3",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR") ? "&4SADECE SAYI"
								: "&4JUST NUMBERS");
			change_price_3 = BasicAnimals.getInstance().getOptions().getString("Change_Price_3").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Change_Name_1"))
				BasicAnimals.getInstance().getOptions().set("Change_Name_1",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR") ? "&0Yeni ismi üst"
								: "&0Write new name");
			change_name_1 = BasicAnimals.getInstance().getOptions().getString("Change_Name_1").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Change_Name_2"))
				BasicAnimals.getInstance().getOptions().set("Change_Name_2",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR") ? "&0kısıma yazınız."
								: "&0upside string area.");
			change_name_2 = BasicAnimals.getInstance().getOptions().getString("Change_Name_2").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Sell_Status_Updated"))
				BasicAnimals.getInstance().getOptions().set("Sell_Status_Updated",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &aSatış durumu güncellendi!"
								: "&8[&bBM&8] &aSell status has been updated!");
			sell_status_updated = BasicAnimals.getInstance().getOptions().getString("Sell_Status_Updated")
					.replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Wrong_Usage"))
				BasicAnimals.getInstance().getOptions().set("Wrong_Usage",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &cYanlış kullandınız!"
								: "&8[&bBM&8] &cWrong usage!");
			wrong_usage = BasicAnimals.getInstance().getOptions().getString("Wrong_Usage").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("User_Not_Online"))
				BasicAnimals.getInstance().getOptions().set("User_Not_Online",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &cKullanıcı aktif değil!"
								: "&8[&bBM&8] &cUser is not online!");
			user_is_not_online = BasicAnimals.getInstance().getOptions().getString("User_Not_Online").replaceAll("&",
					"§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Just_Number"))
				BasicAnimals.getInstance().getOptions().set("Just_Number",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &cLütfen sadece sayı giriniz!"
								: "&8[&bBM&8] &cPlease input just numbers!");
			just_number_pls = BasicAnimals.getInstance().getOptions().getString("Just_Number").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Send_Success"))
				BasicAnimals.getInstance().getOptions().set("Send_Success",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &aIstenilen oyuncuya istediğiniz miktarda bilet gönderildi!"
								: "&8[&bBM&8] &aOperation complete! Ticket's sended.");
			send_success = BasicAnimals.getInstance().getOptions().getString("Send_Success").replaceAll("&", "§");

			if (!BasicAnimals.getInstance().getOptions().isSet("Reload_Success"))
				BasicAnimals.getInstance().getOptions().set("Reload_Success",
						BasicAnimals.getInstance().getConfig().getString("Locale").equals("TR")
								? "&8[&bBM&8] &aYenileme başarılı!"
								: "&8[&bBM&8] &aReload successfull!");
			reload_suceess = BasicAnimals.getInstance().getOptions().getString("Reload_Success").replaceAll("&", "§");

			try {
				BasicAnimals.getInstance().getOptions().save(BasicAnimals.getInstance().getOptionsFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
