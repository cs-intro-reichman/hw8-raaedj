public class Network {

    private User[] users;
    private int userCount;

    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    public User getUser(String name) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equalsIgnoreCase(name)) { // Case-insensitive
                return users[i];
            }
        }
        return null;
    }

    public boolean addUser(String name) {
        if (userCount >= users.length || getUser(name) != null) {
            return false;
        }
        users[userCount++] = new User(name);
        return true;
    }

    public boolean addFollowee(String name1, String name2) {
        if (name1 == null || name2 == null) {
            return false; // Invalid input, return false
        }
        User user1 = getUser(name1);
        User user2 = getUser(name2);
        if (user1 == null || user2 == null) {
            return false; // Either user does not exist
        }
        return user1.addFollowee(name2);
    }
    

    public String recommendWhoToFollow(String name) {
        User user = getUser(name);
        if (user == null) {
            return null;
        }
        User mostRecommendedUserToFollow = null;
        int maxMutualFollowees = 0;
        for (int i = 0; i < userCount; i++) {
            User other = users[i];
            if (other.getName().equalsIgnoreCase(name)) {
                continue;
            }
            int mutualCount = user.countMutual(other);
            if (mutualCount > maxMutualFollowees) {
                maxMutualFollowees = mutualCount;
                mostRecommendedUserToFollow = other;
            }
        }
        return mostRecommendedUserToFollow != null ? mostRecommendedUserToFollow.getName() : null;
    }

    public String mostPopularUser() {
        String mostPopular = null;
        int maxCount = 0;
        for (int i = 0; i < userCount; i++) {
            int count = followeeCount(users[i].getName());
            if (count > maxCount) {
                maxCount = count;
                mostPopular = users[i].getName();
            }
        }
        return mostPopular;
    }

    private int followeeCount(String name) {
        int count = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i].follows(name)) {
                count++;
            }
        }
        return count;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("Network:");
        for (int i = 0; i < userCount; i++) {
            result.append("\n").append(users[i].toString());
        }
        return result.toString();
    }
}
