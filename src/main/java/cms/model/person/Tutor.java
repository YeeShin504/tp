package cms.model.person;

import java.util.Set;

import cms.model.tag.Tag;

/**
 * Represents a tutor in the course management system.
 */
public class Tutor extends Person {

    public Tutor(Name name, Phone phone, Email email, NusId nusId, SocUsername socUsername,
            GithubUsername githubUsername, TutorialGroup tutorialGroup, Set<Tag> tags) {
        super(name, phone, email, nusId, socUsername, githubUsername, tutorialGroup, tags);
    }

    @Override
    public Role getRole() {
        return Role.TUTOR;
    }
}
