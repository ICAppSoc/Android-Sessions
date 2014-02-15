[git subtree Overview / Tutorial][subtreetut]
=============================================

Adding sub-project
------------------
    git remote add -f session-x-remote git@bitbucket.org:justasm/ic-appsoc-android-lesson-x.git

Adding subtree
--------------
    git subtree add --prefix "Session x" session-x-remote master --squash

Updating sub-project
--------------------
    git fetch session-x-remote master
    git subtree pull --prefix "Session x" session-x-remote master --squash

Contributing back to up-stream
------------------------------
Fork and add as another remote:

    git remote add session-x-fork ssh://git@bitbucket.org:justasm/ic-appsoc-android-lesson-x.git

Use subtree push:

    git subtree push --prefix="Session x" session-x-fork master

[subtreetut]: http://blogs.atlassian.com/2013/05/alternatives-to-git-submodule-git-subtree/