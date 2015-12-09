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

    public static Command cancelProject(String projectId) {
        Command command = new Command("cancelProject");
        command.addArgument(projectId);
        return command;
    }

    public static Command addAdminToProject(String usernameToPromote, String projectId) {
        Command command = new Command("addAdminToProject");
        command.addArgument(usernameToPromote);
        command.addArgument(projectId);
        return command;
    }

    public static Command addGoalToProject(Goal goal, String projectId) {
        Command command = new Command("addGoalToProject");
        command.setAttachedObject(goal);
        command.addArgument(projectId);
        return command;
    }

    public static Command addRewardToProject(Reward goal, String projectId) {
        Command command = new Command("addRewardToProject");
        command.setAttachedObject(goal);
        command.addArgument(projectId);
        return command;
    }

    public static Command addQuestionToProject(String question, String projectId) {
        Command command = new Command("addQuestionToProject");
        command.addArgument(question);
        command.addArgument(projectId);
        return command;
    }

    public static Command addOptionToProject(DecisionOption option, String projectId) {
        Command command = new Command("addOptionToProject");
        command.setAttachedObject(option);
        command.addArgument(projectId);
        return command;
    }

    public static Command removeGoalFromProject(Goal goal, String projectId) {
        Command command = new Command("removeGoalFromProject");
        command.setAttachedObject(goal);
        command.addArgument(projectId);
        return command;
    }

    public static Command removeRewardFromProject(String rewardId) {
        Command command = new Command("removeRewardFromProject");
        command.addArgument(rewardId);
        return command;
    }

    public static Command getProject(String projectId) {
        Command command = new Command("getProject");
        command.addArgument(projectId);
        return command;
    }

    public static Command getRewardsFromProject(String projectId) {
        Command command = new Command("getRewardsFromProject");
        command.addArgument(projectId);
        return command;
    }

    public static Command getGoalsFromProject(String projectId) {
        Command command = new Command("getGoalsFromProject");
        command.addArgument(projectId);
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

    public static Command getProjectMessages(String projectId) {
        Command command = new Command("getProjectMessages");
        command.addArgument(projectId);
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

    public static Command sendRewardToUser(String rewardId, String toUserId) {
        Command command = new Command("sendRewardToUser");
        command.addArgument(rewardId);
        command.addArgument(toUserId);
        return command;
    }

    public static Command pledge(Pledge pledge) {
        Command command = new Command("pledge");
        command.setAttachedObject(pledge);
        return command;
    }

}
