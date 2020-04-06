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
    btnReservationArt : number;
    changeHalfDay: boolean;
    goOutArt: number;
    setPreClockArt: number;
    changeClockArt: number;
    changeCalArt: number;
    usrArt: number;
    audioType: number;
}
const DATE_FORMAT = 'DD-MM-YYYY';
const TIME_FORMAT = 'HH:mm:ss';
class StampClock {
    time: KnockoutObservable<Date> = ko.observable(new Date());
    displayDate: KnockoutObservable<String>;
    displayTime: KnockoutObservable<String>;
    constructor() {
        let self = this;
        self.displayDate = ko.observable(moment(self.time()).format(DATE_FORMAT));
        self.displayTime = ko.observable(moment(self.time()).format(TIME_FORMAT));
        self.time.subscribe((val) => {
            self.displayDate(moment(val).format(DATE_FORMAT));
            self.displayTime(moment(val).format(TIME_FORMAT));
        });
        setInterval(() => {
            self.time(new Date());
        },1000);
    }
}