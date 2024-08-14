## WinBoll 主机编译事项提醒

# 类库发布以下面命令
git pull && bash .winboll/bashPublishAPKAddTag.sh && bash gradlew publishReleasePublicationToWinBollReleaseRepository && bash .winboll/bashCommitLibReleaseBuildFlagInfo.sh

# 应用发布以下命令
git pull && bash .winboll/bashPublishAPKAddTag.sh

## 编译时提问。Add Github Workflows Tag? (yes/no)
回答yes: 将会添加一个 GitHub 工作流标签
        GitHub 仓库会执行以该标签为标志的编译工作流。
回答no: 就忽略 GitHub 标签，忽略 GitHub 工作流调用。

## Github Workflows 工作流设置注意事项
应用名称改变时需要修改.github/workflows/android.yml文件设置，
在第79行：asset_name: 处有应用包名称设置。
