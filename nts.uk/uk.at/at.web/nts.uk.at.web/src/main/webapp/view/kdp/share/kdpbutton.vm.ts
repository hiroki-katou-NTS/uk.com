module nts.uk.at.view.kdp.share {
    const layoutType = {
        LARGE_2_SMALL_4: 0,
        SMALL_8:1
    }
    const DEFAULT_GRAY = '#E8E9EB';
    const GET_HIGHLIGHT_SETTING_URL = 'at/record/stamp/management/personal/stamp/getHighlightSetting';
    export class StampButtonLayOut {
        buttonSettings: KnockoutObservableArray<ButtonSetting> = ko.observableArray([]);
        buttonLayoutType: KnockoutObservable<number> = ko.observable(0);
        useHighlightFunction: KnockoutObservable<StampToSuppress> = ko.observable({});
        parentVM: KnockoutObservable<any>;
        selectedLayout: KnockoutObservable<any> = ko.observable({});
        constructor(params: any) {
            let self = this;
            self.parentVM = ko.observable(params.parent.content);
            self.useHighlightFunction(params.highlightSetting());
            if(params.data()) {
                let layout = _.clone(params.data(), true);
                self.selectedLayout(layout);
                self.buttonLayoutType = ko.observable(layout.buttonLayoutType);
                self.correntBtnSetting(layout.buttonSettings);
            };

            params.highlightSetting.subscribe(() => {
                self.reloadHighLight();
            });
        }

        public correntBtnSetting(btnSets: Array<ButtonSetting>) {
            let self = this;
            let btnList = [];
            let btnNum = self.buttonLayoutType() === layoutType.LARGE_2_SMALL_4 ? 6 : 8;
            let clBtnSets = _.clone(btnSets, true);
            for (let idx = 1; idx <= btnNum; idx++) {
                let btn = _.find(clBtnSets, (btn) => {return btn.btnPositionNo  === idx});
                if(btn && !btn.onClick) {
                    btn.onClick = () => {};
                }
                if (btn) {
                    btn.idx = idx;
                }
                // A14 時刻に従ってボタンの色が変わる処理
                let btnBackGroundColor = btn ? btn.btnBackGroundColor : '';
                if(self.useHighlightFunction().isUse && btn) {
                    btnBackGroundColor = DEFAULT_GRAY;
                    if ( btn.btnDisplayType == 1 ) {
                        btnBackGroundColor = !self.useHighlightFunction().goingToWork ? btn.btnBackGroundColor : DEFAULT_GRAY;
                    }  
                    if ( btn.btnDisplayType == 2) {
                        btnBackGroundColor = !self.useHighlightFunction().departure ? btn.btnBackGroundColor : DEFAULT_GRAY;
                    } 
                    if ( btn.btnDisplayType == 3 ) {
                        btnBackGroundColor = !self.useHighlightFunction().goOut ? btn.btnBackGroundColor : DEFAULT_GRAY;
                    }  
                    if ( btn.btnDisplayType == 4 ) {
                        btnBackGroundColor = !self.useHighlightFunction().turnBack ? btn.btnBackGroundColor : DEFAULT_GRAY;
                    }
                    btn.btnBackGroundColor = btnBackGroundColor;
                }
                
                btnList.push(btn && btn.usrArt == 1 ? btn : {idx: idx, btnPositionNo: -1, btnName: '', btnBackGroundColor: '', btnTextColor: '', onClick: () => {}});
            }
         
            self.buttonSettings(btnList);
        }

        public reloadHighLight() {
            let self = this;
            if(self.selectedLayout().buttonSettings) {
                if(self.useHighlightFunction().isUse) {
                    nts.uk.request.ajax('at', GET_HIGHLIGHT_SETTING_URL).done((res) => {
                        res.isUse = false;
                        self.useHighlightFunction(res);
                        console.log(self.selectedLayout());
                        self.correntBtnSetting(self.selectedLayout().buttonSettings);
                    });
                }
            }
        }

    }
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
    btnDisplayType: number;
}

interface StampToSuppress {
    goingToWork: boolean;
    departure: boolean;
    goOut: boolean;
    turnBack: boolean;
    isUse: boolean;
}

ko.components.register('stamp-layout-button', {
    viewModel: nts.uk.at.view.kdp.share.StampButtonLayOut, template: `
    <div data-bind="visible: buttonSettings().length > 0">
        
        <div data-bind="visible: buttonLayoutType() != 1">
            <div class="btn-grid-container cf" data-bind="foreach: buttonSettings">
                <div class="stamp-rec-btn-container pull-left" data-bind="css: 'btn-pos-' + idx">
                        <button class="stamp-rec-btn" id=""
                            data-bind="text: btnName, 
                            style:{ 'background-color' :  btnBackGroundColor, color :  btnTextColor }, 
                            click: function(data, event) { onClick($parent.parentVM(), $parent.selectedLayout()) }, 
                            visible: btnPositionNo != -1
                            "></button>
                </div>
            </div>
        </div>

        <div data-bind="visible: buttonLayoutType() == 1">
            <div class="btn-grid-container square-container cf" data-bind="foreach: buttonSettings">
                <div class="stamp-square-btn-container pull-left">
                        <button class="stamp-rec-btn" id=""
                            data-bind="text: btnName, style:{ 'background-color' :  btnBackGroundColor, color :  btnTextColor }, click: function(data, event) { onClick($parent.parentVM(), $parent.selectedLayout()) }, visible: btnPositionNo != -1"></button>
                </div>
            </div>
        </div>
        
    </div>
`});




