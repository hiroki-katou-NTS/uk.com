interface StampSetting {
    buttonEmphasisArt: boolean;
    historyDisplayMethod: number;
    correctionInterval: number;
    textColor: string;
    backGroundColor: string;
    resultDisplayTime: number;
    pageLayouts: Array<PageLayout>;
}

interface PageLayout {
    pageNo: number;
    stampPageName: string;
    stampPageComment: string;
    stampPageCommentColor: string;
    buttonLayoutType: number;
    buttonSettings: Array<ButtonSetting>;
}

interface ButtonSetting {
    btnPositionNo: number;
    btnName: string;
    btnTextColor: string;
    btnBackGroundColor: string;
    btnReservationArt: number;
    changeHalfDay: boolean;
    goOutArt: number;
    setPreClockArt: number;
    changeClockArt: number;
    changeCalArt: number;
    usrArt: number;
    audioType: number;
    onClick: any;
}

const DATE_FORMAT = 'YYYY年 M月 D日 (ddd)';
const TIME_FORMAT = 'HH:mm';
class StampClock {
    time: KnockoutObservable<Date> = ko.observable(new Date());
    displayDate: KnockoutObservable<String>;
    displayTime: KnockoutObservable<String>;

    constructor() {
        let self = this;
        moment.locale('ja');
        self.displayDate = ko.observable(moment(self.time()).format(DATE_FORMAT));
        self.displayTime = ko.observable(moment(self.time()).format(TIME_FORMAT));
        setInterval(() => {
            nts.uk.request.syncAjax("com", "server/time/now/").done((res) => {
                console.log(res);
                self.displayTime(moment.utc(res).format(TIME_FORMAT));
            });
        }, 2000);
    }

    public addCorrectionInterval(minute: number) {
        let self = this;
        setInterval(() => {
            nts.uk.request.syncAjax("com", "server/time/now/").done((res) => {
                self.displayDate(moment.utc(res).format(DATE_FORMAT));
            });
        }, minute * 60000);
    }
}

class StampTab {
    tabs: KnockoutObservableArray<NtsTabPanelModel> = ko.observableArray([]);
    selectedTab: KnockoutObservable<string> = ko.observable('');
    stampPageComment: KnockoutObservable<string> = ko.observable('');
    stampPageCommentColor: KnockoutObservable<string> = ko.observable('');
    layouts: KnockoutObservableArray<PageLayout> = ko.observableArray([]);
    
    constructor() {
        let self = this;
        self.selectedTab.subscribe((val) => {
            let stampTab = _.find(self.tabs(), (tab) => { return tab.id == val });
            if(stampTab) {
                self.stampPageComment(stampTab.stampPageComment);
                self.stampPageCommentColor(stampTab.color);
            }
        });
    }

    public bindData(layouts: Array<PageLayout>) {
        let self = this;
        let tabs = [];
        self.layouts(layouts);
        for (let idx = 1; idx <= 5; idx++) {
            let layout = _.find(layouts, (ly) => { return ly.pageNo === idx });
            if(layout) {
                tabs.push({ id: 'tab-' + idx, 
                            title: layout ? layout.stampPageName : '', 
                            content: layout ? '.tab-content-' + layout.pageNo : '',
                            stampPageComment: layout.stampPageComment,
                            color: layout.stampPageCommentColor,
                            enable: ko.observable(true), 
                            visible: ko.observable(true)});
            }
        };
        console.log(tabs);
        self.tabs(tabs);
        self.selectedTab('tab-' + layouts[0].pageNo);
        self.selectedTab.valueHasMutated();
    }
    
}