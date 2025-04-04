package ru.egmohf.cyberjur.api.objects;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UserObject {
    private String login;
    private String email;
    private String telegram;
    private String publicId;
    private UserInfo info;
    private int number;
    private UserPersonalization personalization;
    private Map<CollectionName, List<Integer>> collections;
    private int thanks;
    private int giveThanks;
    private UserRole role;
    private Customization customization;
    private AchievementsNames[] achievements;
    private boolean verifying;
    private String[] groups;
    private UserWordle wordle;
    private int completedTasks;
    private UserTasks tasks;
    private LocalDate lastVisited;
    private Notice[] notices;
    private Map<SocialNetwork, String> socialNetworks;
    private Map<AchievementsNames, Integer> statistics;
    private int realMoney;
    private int[] cards;
    private Map<String, Integer[]> decks;
    private String[] blockedWidgets;
    private String[] usedPromoCodes;
    private int openedPokemons;
    private LocalDate lastFoundPokemon;
    private String[] friends;
    private Map<Actions, Integer> actions;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public UserPersonalization getPersonalization() {
        return personalization;
    }

    public void setPersonalization(UserPersonalization personalization) {
        this.personalization = personalization;
    }

    public Map<CollectionName, List<Integer>> getCollections() {
        return collections;
    }

    public void setCollections(Map<CollectionName, List<Integer>> collections) {
        this.collections = collections;
    }

    public int getThanks() {
        return thanks;
    }

    public void setThanks(int thanks) {
        this.thanks = thanks;
    }

    public int getGiveThanks() {
        return giveThanks;
    }

    public void setGiveThanks(int giveThanks) {
        this.giveThanks = giveThanks;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Customization getCustomization() {
        return customization;
    }

    public void setCustomization(Customization customization) {
        this.customization = customization;
    }

    public AchievementsNames[] getAchievements() {
        return achievements;
    }

    public void setAchievements(AchievementsNames[] achievements) {
        this.achievements = achievements;
    }

    public boolean isVerifying() {
        return verifying;
    }

    public void setVerifying(boolean verifying) {
        this.verifying = verifying;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public UserWordle getWordle() {
        return wordle;
    }

    public void setWordle(UserWordle wordle) {
        this.wordle = wordle;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public UserTasks getTasks() {
        return tasks;
    }

    public void setTasks(UserTasks tasks) {
        this.tasks = tasks;
    }

    public LocalDate getLastVisited() {
        return lastVisited;
    }

    public void setLastVisited(LocalDate lastVisited) {
        this.lastVisited = lastVisited;
    }

    public Notice[] getNotices() {
        return notices;
    }

    public void setNotices(Notice[] notices) {
        this.notices = notices;
    }

    public Map<SocialNetwork, String> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(Map<SocialNetwork, String> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public Map<AchievementsNames, Integer> getStatistics() {
        return statistics;
    }

    public void setStatistics(Map<AchievementsNames, Integer> statistics) {
        this.statistics = statistics;
    }

    public int getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(int realMoney) {
        this.realMoney = realMoney;
    }

    public int[] getCards() {
        return cards;
    }

    public void setCards(int[] cards) {
        this.cards = cards;
    }

    public Map<String, Integer[]> getDecks() {
        return decks;
    }

    public void setDecks(Map<String, Integer[]> decks) {
        this.decks = decks;
    }

    public String[] getBlockedWidgets() {
        return blockedWidgets;
    }

    public void setBlockedWidgets(String[] blockedWidgets) {
        this.blockedWidgets = blockedWidgets;
    }

    public String[] getUsedPromoCodes() {
        return usedPromoCodes;
    }

    public void setUsedPromoCodes(String[] usedPromoCodes) {
        this.usedPromoCodes = usedPromoCodes;
    }

    public int getOpenedPokemons() {
        return openedPokemons;
    }

    public void setOpenedPokemons(int openedPokemons) {
        this.openedPokemons = openedPokemons;
    }

    public LocalDate getLastFoundPokemon() {
        return lastFoundPokemon;
    }

    public void setLastFoundPokemon(LocalDate lastFoundPokemon) {
        this.lastFoundPokemon = lastFoundPokemon;
    }

    public String[] getFriends() {
        return friends;
    }

    public void setFriends(String[] friends) {
        this.friends = friends;
    }

    public Map<Actions, Integer> getActions() {
        return actions;
    }

    public void setActions(Map<Actions, Integer> actions) {
        this.actions = actions;
    }

    public static class UserInfo {
        private String name;
        private String surname;
        private String birthday;
        private Gender gender;

        public enum Gender {
            MALE(1), FEMALE(0);

            private final int code;

            Gender(int code) {
                this.code = code;
            }

            public int getCode() {
                return code;
            }
        }
    }

    public static class UserPersonalization {
        private String avatar;
        private String background;
        private String border;
        private String about;
        private String cubes;
        private String pokemon;
        private String smiles;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getBorder() {
            return border;
        }

        public void setBorder(String border) {
            this.border = border;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getCubes() {
            return cubes;
        }

        public void setCubes(String cubes) {
            this.cubes = cubes;
        }

        public String getPokemon() {
            return pokemon;
        }

        public void setPokemon(String pokemon) {
            this.pokemon = pokemon;
        }

        public String getSmiles() {
            return smiles;
        }

        public void setSmiles(String smiles) {
            this.smiles = smiles;
        }
    }

    public enum CollectionName {
        @SerializedName("azuki") AZUKI,
        @SerializedName("beanz") BEANZ,
        @SerializedName("doggy") DOGGY,
        @SerializedName("gazer") GAZER
    }

    public enum UserRole{
        @SerializedName("admin") ADMIN,
        @SerializedName("guest") GUEST,
        @SerializedName("student") STUDENT,
        @SerializedName("teacher") TEACHER
    }

    public static class Customization{
        private Map<ThemeName, String> theme;
        private boolean isShortToolbar;

        public Map<ThemeName, String> getTheme() {
            return theme;
        }

        public void setTheme(Map<ThemeName, String> theme) {
            this.theme = theme;
        }

        public boolean isShortToolbar() {
            return isShortToolbar;
        }

        public void setShortToolbar(boolean shortToolbar) {
            isShortToolbar = shortToolbar;
        }

        public enum ThemeName{
            @SerializedName("--first-bg-color") FIRST_BG_COLOR,
            @SerializedName("--second-bg-color") SECOND_BG_COLOR,
            @SerializedName("--red-color") RED_COLOR,
            @SerializedName("--blue-color") BLUE_COLOR,
            @SerializedName("--green-color") GREEN_COLOR,
            @SerializedName("--white-color") WHITE_COLOR,
            @SerializedName("--black-color") BLACK_COLOR,
            @SerializedName("--gray-color") GRAY_COLOR,
            @SerializedName("--yellow-color") YELLOW_COLOR
        }
    }

    public enum AchievementsNames{
        @SerializedName("Cubes") CUBES,
        @SerializedName("Case") CASE,
        @SerializedName("Volunteer") VOLUNTEER,
        @SerializedName("Solution") SOLUTION,
        @SerializedName("KeyboardTrys") KEYBOARD_TRYS,
        @SerializedName("Fan") FAN,
        @SerializedName("Collection") COLLECTION,
        @SerializedName("DaysSeven") DAYS_SEVEN,
        @SerializedName("FullVisits") FULL_VISITS,
        @SerializedName("YearLearn") YEAR_LEARN,
        @SerializedName("HistoryMoney") HISTORY_MONEY
    }

    public static class UserWordle {
        private LocalDate lastGame;
        private String id;
        private int winGames;
        private int loseGames;
        private int recordWinGames;
        private int averageTrys;
        private boolean notifyInTelegram;
        private LocalDate lastNotify;

        public LocalDate getLastGame() {
            return lastGame;
        }

        public void setLastGame(LocalDate lastGame) {
            this.lastGame = lastGame;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getWinGames() {
            return winGames;
        }

        public void setWinGames(int winGames) {
            this.winGames = winGames;
        }

        public int getLoseGames() {
            return loseGames;
        }

        public void setLoseGames(int loseGames) {
            this.loseGames = loseGames;
        }

        public int getRecordWinGames() {
            return recordWinGames;
        }

        public void setRecordWinGames(int recordWinGames) {
            this.recordWinGames = recordWinGames;
        }

        public int getAverageTrys() {
            return averageTrys;
        }

        public void setAverageTrys(int averageTrys) {
            this.averageTrys = averageTrys;
        }

        public boolean isNotifyInTelegram() {
            return notifyInTelegram;
        }

        public void setNotifyInTelegram(boolean notifyInTelegram) {
            this.notifyInTelegram = notifyInTelegram;
        }

        public LocalDate getLastNotify() {
            return lastNotify;
        }

        public void setLastNotify(LocalDate lastNotify) {
            this.lastNotify = lastNotify;
        }
    }

    public static class UserTasks {
        private Map<String, Boolean> askedTask;
        private Map<String, LocalDate> lastGivenTask;

        public Map<String, Boolean> getAskedTask() {
            return askedTask;
        }

        public void setAskedTask(Map<String, Boolean> askedTask) {
            this.askedTask = askedTask;
        }

        public Map<String, LocalDate> getLastGivenTask() {
            return lastGivenTask;
        }

        public void setLastGivenTask(Map<String, LocalDate> lastGivenTask) {
            this.lastGivenTask = lastGivenTask;
        }
    }

    public static class Notice{
        private int id;
        private String text;
        private LocalDate date;
        private boolean isRead;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public boolean isRead() {
            return isRead;
        }

        public void setRead(boolean read) {
            isRead = read;
        }
    }

    public enum SocialNetwork{
        @SerializedName("telegram") TELEGRAM,
        @SerializedName("tiktok") TIKTOK,
        @SerializedName("vk") VK,
        @SerializedName("github") GITHUB,
        @SerializedName("youtube") YOUTUBE
    }

    public enum Actions{
        @SerializedName("Альфа") ALPHA,
        @SerializedName("ВТБ") VTB,
        @SerializedName("Сбер") SBER,
        @SerializedName("Тинька") TINKOFF
    }
}