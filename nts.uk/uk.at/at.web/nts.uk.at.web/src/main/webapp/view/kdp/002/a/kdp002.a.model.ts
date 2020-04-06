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
        self.time.subscribe((val) => {
            self.displayDate(moment(val).format(DATE_FORMAT));
            self.displayTime(moment(val).format(TIME_FORMAT));
        });
        setInterval(() => {
            self.time(new Date());
        }, 1000);
    }
}

class StampTab {

    tabs: KnockoutObservableArray<NtsTabPanelModel>;
    selectedTab: KnockoutObservable<string>;
    stampPageComment: KnockoutObservable<string>;
    layouts: KnockoutObservableArray<PageLayout>;
    constructor() {
        let self = this;
        self.tabs = ko.observableArray([]);
        self.layouts = ko.observableArray([]);
        self.selectedTab = ko.observable('tab-1');
        self.stampPageComment = ko.observable('');
        self.selectedTab.subscribe((val) => {

        });
    }

    public bindData(layouts: Array<PageLayout>) {
        let self = this;
        let tabs = [];
        self.layouts(layouts);
        for (let idx = 1; idx <= 5; idx++) {
            let layout = _.findLast(layouts, (ly) => { return ly.pageNo === idx });
            tabs.push({ id: 'tab-' + idx, 
                        title: layout ? layout.stampPageName : '', 
                        content: layout ? '.tab-content-' + layout.pageNo : '',
                        enable: ko.observable(_.isNull(layout)), 
                        visible: ko.observable(_.isNull(layout))});
        };
        self.tabs(tabs);
        self.selectedTab('tab-' + layouts[0].pageNo);
    }

    public checkVisible(pageNo: number) {
        let self = this;
        // console.log(self.layouts());
        // console.log(_.findLast(self.layouts(), (ly) => { ly.pageNo === pageNo }));
        // return _.isNull(_.findLast(self.layouts(), (ly) => { ly.pageNo === pageNo }));
        return true;
    }
}