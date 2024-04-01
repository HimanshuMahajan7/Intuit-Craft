public enum Status {
    DELETED,
    ACTIVE;
}

public class User {
    String id;
    String name;
    String email;
    String password;
    String profilePic;
    Short status = 1;
}

public class Post {
    String id;
    User user;
    String text;
    int commentCount;
    int likeCount;
    int dislikeCount;
    Date createdAt;
    Date modifiedAt;
    short status;
}


public class Comment {
    String id;
    String parentId;
    User user;
    String text;
    int commentLevel;
    int replyCount;
    int likeCount;
    int dislikeCount;
    Date createdAt;
    Date modifiedAt;
    short status;
}

public enum EngagementType {
    DISLIKE,
    LIKE
}

public class Engagement {
    String resourceId;
    String userId;
    EngagementType engagement;
    Date createdAt;
    Date modifiedAt;
}

public interface UserService {
    User registerUser(UserRegisterDto userRegisterDto);
    User login(UserLoginDto userLoginDto);
    User getUser(String userId);
}

public interface PostService {
    Post createPost(String authorId, Post post);
    Post getPost(String postId);
    List<Post> getPosts(int page);
}

public interface CommentService {
    Comment createComment(String authorId, Comment comment);
    Comment updateComment(String commentId, String authorId, CommentUpdate comment);
    Comment getComment(String commentId);
    List<Comment> getComments(int page);
    List<Comment> getCommentsOnAPost(String postId, int page);
    List<Comment> getRepliesOnAComment(String commentId, int page);
    boolean softDeleteComment(String commentId);
}

public interface EngagementService {
    Engagement createEngagement(String resourceId, String userId, Engagement engagementType);
    List<Engagement> getEngagements(String resourceId, EngagementType engagementType);
}