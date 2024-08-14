#!/usr/bin/bash
git pull && bash .winboll/bashPublishAPKAddTag.sh && bash gradlew publishReleasePublicationToWinBollReleaseRepository && bash .winboll/bashCommitLibReleaseBuildFlagInfo.sh
