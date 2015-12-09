package fundstarter;

/**
 * Created by Miguel Prata Leal on 09/12/15.
 */
public class ServerCommandFactory {

    public static Command login(String username, String password) {
        Command command = new Command("login");
        command.addArgument(username);
        command.addArgument(password);
        return command;
    }

    public static Command signup(String username, String password) {
        Command command = new Command("signUp");
        command.addArgument(username);
        command.addArgument(password);
        return command;
    }

    public static Command logout() {
        Command command = new Command("logout");
        return command;
    }

    public static Command getBalance() {
        return new Command("getBalance");
    }

    public static Command newProject(Project project) {
        Command command = new Command("newProject");
        command.setAttachedObject(project);
        return command;
    }

    public static Command cancelProject(int projectId) {
        Command command = new Command("cancelProject");
        command.addArgument(Integer.toString(projectId));
        return command;
    }

    public static Command addAdminToProject(String usernameToPromote, int projectId) {
        Command command = new Command("addAdminToProject");
        command.addArgument(usernameToPromote);
        command.addArgument(Integer.toString(projectId));
        return command;
    }

    public static Command addGoalToProject(Goal goal, int projectId) {
        Command command = new Command("addGoalToProject");
        command.setAttachedObject(goal);
        command.addArgument(Integer.toString(projectId));
        return command;
    }

    public static Command addRewardToProject(Reward goal, int projectId) {
        Command command = new Command("addRewardToProject");
        command.setAttachedObject(goal);
        command.addArgument(Integer.toString(projectId));
        return command;
    }

    public static Command addQuestionToProject(String question, int projectId) {
        Command command = new Command("addQuestionToProject");
        command.addArgument(question);
        command.addArgument(Integer.toString(projectId));
        return command;
    }

    public static Command addOptionToProject(DecisionOption option, int projectId) {
        Command command = new Command("addOptionToProject");
        command.setAttachedObject(option);
        command.addArgument(Integer.toString(projectId));
        return command;
    }

    public static Command removeGoalFromProject(Goal goal, int projectId) {
        Command command = new Command("removeGoalFromProject");
        command.setAttachedObject(goal);
        command.addArgument(Integer.toString(projectId));
        return command;
    }

    public static Command removeRewardFromProject(int rewardId, int projectId) {
        Command command = new Command("removeRewardFromProject");
        command.addArgument(Integer.toString(rewardId));
        command.addArgument(Integer.toString(projectId));
        return command;
    }

    public static Command getProject(int projectId) {
        Command command = new Command("getProject");
        command.addArgument(Integer.toString(projectId));
        return command;
    }

    public static Command getRewardsFromProject(int projectId) {
        Command command = new Command("getRewardsFromProject");
        command.addArgument(Integer.toString(projectId));
        return command;
    }

    public static Command getGoalsFromProject(int projectId) {
        Command command = new Command("getGoalsFromProject");
        command.addArgument(Integer.toString(projectId));
        return command;
    }

    public static Command getPledgesFromUser() {
        Command command = new Command("getPledgesFromUser");
        return command;
    }

    public static Command getRewardsFromUser() {
        Command command = new Command("getRewardsFromUser");
        return command;
    }

    public static Command getProjectMessages(int projectId) {
        Command command = new Command("getProjectMessages");
        command.addArgument(Integer.toString(projectId));
        return command;
    }

    public static Command getUserMessages() {
        Command command = new Command("getUserMessages");
        return command;
    }

    public static Command getExpiredProjects() {
        Command command = new Command("getExpiredProjects");
        return command;
    }

    public static Command getInProgressProjects() {
        Command command = new Command("getInProgressProjects");
        return command;
    }

    public static Command sendMessageFromProject(Message message) {
        Command command = new Command("sendMessageFromProject");
        command.setAttachedObject(message);
        return command;
    }

    public static Command sendMessageToProject(Message message) {
        Command command = new Command("sendMessageToProject");
        command.setAttachedObject(message);
        return command;
    }

    public static Command sendRewardToUser(int rewardId, int toUserId) {
        Command command = new Command("sendRewardToUser");
        command.addArgument(Integer.toString(rewardId));
        command.addArgument(Integer.toString(toUserId));
        return command;
    }

    public static Command pledge(Pledge pledge) {
        Command command = new Command("pledge");
        command.setAttachedObject(pledge);
        return command;
    }

}
