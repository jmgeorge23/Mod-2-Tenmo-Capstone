package com.techelevator.tenmo;

import java.math.BigDecimal;
import java.util.Scanner;

import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfers;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TenmoService;
import com.techelevator.view.ConsoleService;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_VIEW_TRANSFERS_BY_ID = "View a specific transfer";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_VIEW_TRANSFERS_BY_ID, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
		
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private Accounts accounts;
    private TenmoService tenmoService;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new TenmoService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService, TenmoService tenmoService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.tenmoService = tenmoService;
	}

	public void run() {
		System.out.println("   ||====================================================================||\r\n" + 
				"   ||//$\\\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\//$\\\\||\r\n" + 
				"   ||(100)==================| TENMO  BIG  DECIMALS |================(100)||\r\n" + 
				"   ||\\\\$//        ~         '------========--------'                \\\\$//||\r\n" + 
				"   ||<< /        /$\\              // ____ \\\\                         \\ >>||\r\n" + 
				"   ||>>|  12    //L\\\\            //(///^^) \\\\         L38036133B   12 |<<||\r\n" + 
				"   ||<<|        \\\\ //           ||( ||  >\\  ||                        |>>||\r\n" + 
				"   ||>>|         \\$/            ||'--\\ c-/  ||        One Hundred     |<<||\r\n" + 
				"||====================================================================||>||\r\n" + 
				"||//$\\\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\//$\\\\||<||\r\n" + 
				"||(100)==================| TENMO  BIG  DECIMALS |================(100)||>||\r\n" + 
				"||\\\\$//        ~         '------========--------'                \\\\$//||\\||\r\n" + 
				"||<< /        /$\\              // /*** \\\\                         \\ >>||)||\r\n" + 
				"||>>|  12    //L\\\\            // /-O-O) \\\\         L38036133B   12 |<<||/||\r\n" + 
				"||<<|        \\\\ //           || <|   >\\  ||                        |>>||=||\r\n" + 
				"||>>|         \\$/            ||  \\  c//  ||        One Hundred     |<<||\r\n" + 
				"||<<|      L38036133B        *\\\\  |\\_/  //* series                 |>>||\r\n" + 
				"||>>|  12                     *\\\\/___\\_//*   2020                  |<<||\r\n" + 
				"||<<\\      Treasurer     ______/ BIDEN  \\________     Secretary 12 />>||\r\n" + 
				"||//$\\~ Hannah S & Jon G ~|UNITED STATES OF AMERICA|~             /$\\\\||\r\n" + 
				"||(100)=================== ONE HUNDRED BIG DECIMALS =============(100)||\r\n" + 
				"||\\\\$//\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\\\$//||\r\n" + 
				"||====================================================================||\r\n" + 
				"");
		
		System.out.println(" |          ___   _                           | \r\n" + 
				" |  __  __   |   |_  ._   ._ _    _   __  __  | \r\n" + 
				" |           |   |_  | |  | | |  (_)          | \r\n" + 
				" |                                            |");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				try {
					viewCurrentBalance();
				} catch (AuthenticationServiceException e) {
					System.out.println("You are not Authorized to access this information.");
				}
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				try {
					viewTransferHistory();
				} catch (AuthenticationServiceException e) {
					System.out.println("You are not Authorized to access this information.");
				}
			}else if(MAIN_MENU_OPTION_VIEW_TRANSFERS_BY_ID.equals(choice)) {
				try {
					viewAllTransfersById();
				}catch(AuthenticationServiceException e){
					System.out.println("You are not Authorized to access this information.");
				}
			}
			else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				try {
					sendBucks();
				} catch (AuthenticationServiceException e) {
					System.out.println("You are not Authorized to access this information.");
				}
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() throws AuthenticationServiceException {
		System.out.println(" |           _                                 _                                      | \r\n" + 
				" |  __  __  /        ._  ._   _   ._   _|_    |_)   _.  |   _.  ._    _   _   __  __  | \r\n" + 
				" |          \\_  |_|  |   |   (/_  | |   |_    |_)  (_|  |  (_|  | |  (_  (/_          | \r\n" + 
				" |                                                                                    |");
		System.out.println("Current Balance is: " + tenmoService.getBalance(currentUser));
	}
	
	private void viewAllTransfersById() throws AuthenticationServiceException{
		Scanner sc = new Scanner(System.in);
		System.out.print("What transfer id?: ");
		String id = sc.nextLine();
		Transfers transfer = tenmoService.getTransferByTransferId(Integer.parseInt(id), currentUser.getToken());
		if(transfer.getTransfer_status_id() == 2) {
			
			System.out.println(transfer.toString() + " ACCEPTED!");
		}else if(transfer.getTransfer_status_id() ==  3) {
			
			System.out.println(transfer.toString() + " REJECTED :(");
		}else {
			System.out.println(transfer.toString() + " PENDING");
		}
	}

	private void viewTransferHistory() throws AuthenticationServiceException {
	Transfers[] transfers = tenmoService.getAllTransactionsByUsers(currentUser.getUser().getId(), currentUser.getToken());
		for(Transfers T : transfers) {
			if(T.getTransfer_status_id() == 2) {
				
				System.out.println(T.toString() + " ACCEPTED!");
			}else if(T.getTransfer_status_id() ==  3) {
				
				System.out.println(T.toString() + " REJECTED :(");
			}else {
				System.out.println(T.toString() + " PENDING");
			}
			
		}
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() throws AuthenticationServiceException {
		
		User[] user = tenmoService.getAll(currentUser.getToken());
		String[] usernames = new String[user.length];
		for(int i = 0; i< user.length; i++) {
			usernames[i] = user[i].getUsername();
		}
		System.out.println("Choose which user you want to send money to");
		String choice = (String)console.getChoiceFromOptions(usernames);
		if(choice.equals(currentUser.getUser().getUsername())){
			System.out.println("Cannot send money to yourself");
			
		}else {
		for(User us: user) {
			
			if(us.getUsername().equals(choice)) {
				System.out.println("------------------------------------------");
				System.out.println("User ID" + "      " + "Name");
				System.out.println(currentUser.getUser().getId() +"         " + currentUser.getUser().getUsername() );
				System.out.println(us.getId() +"         " + us.getUsername() );
				System.out.println("------------------------------------------");
				Scanner sc = new Scanner(System.in);
				
				System.out.print("Enter Amount: ");
				String inAmount = sc.nextLine();
				BigDecimal amount = new BigDecimal(inAmount);
					if(tenmoService.getBalance(currentUser).compareTo(amount) >= 0) {
						System.out.println("\n|          ___                      _                                                            | \r\n" + 
								" |  __  __   |   ._   _.  ._    _  _|_   _   ._     /\\    _   _   _   ._   _|_   _    _|  __  __  | \r\n" + 
								" |           |   |   (_|  | |  _>   |   (/_  |     /--\\  (_  (_  (/_  |_)   |_  (/_  (_|          | \r\n" + 
								" |                                                                    |                           |  \r\n" + 
								"");
						tenmoService.addTransfer(2, 2, currentUser.getUser().getId(), us.getId(), amount, currentUser.getToken());
					}else {
						System.out.println("\n|          ___                      _              _                                  | \r\n" + 
								" |  __  __   |   ._   _.  ._    _  _|_   _   ._    | \\   _   ._   o   _    _|  __  __  | \r\n" + 
								" |           |   |   (_|  | |  _>   |   (/_  |     |_/  (/_  | |  |  (/_  (_|          | \r\n" + 
								" |                                                                                     | ");
						tenmoService.addTransfer(2, 3, currentUser.getUser().getId(), us.getId(), amount, currentUser.getToken());
					}
				}
		}
		}
		
		
		
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
	
	
}
