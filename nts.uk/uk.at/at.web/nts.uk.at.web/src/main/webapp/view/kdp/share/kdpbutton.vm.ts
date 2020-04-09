module nts.uk.at.view.kdp.share {
    const layoutType = {
        LARGE_2_SMALL_4: 0,
        SMALL_8:1
    }
    export class StampButtonLayOut {
        buttonSettings: KnockoutObservableArray<ButtonSetting> = ko.observableArray([]);
        buttonLayoutType: KnockoutObservable<number> = ko.observable(0);
        parentVM: KnockoutObservable<any>;
        constructor(params: any) {
            let self = this;
            self.parentVM = ko.observable(params.parent.content);
            console.log(params.data());
            if(params.data()) {
                let layout = params.data();
                self.buttonLayoutType = ko.observable(layout.buttonLayoutType);
                self.correntBtnSetting(layout.buttonSettings, params.clickBinding);
            };
        }

        public correntBtnSetting(btnSets: Array<ButtonSetting>, clickBinding: any) {
            let self = this;
            let btnList = [];
            let btnNum = self.buttonLayoutType() === layoutType.LARGE_2_SMALL_4 ? 6 : 8;
            for (let idx = 1; idx <= btnNum; idx++) {
                let btn = _.find(btnSets, (btn) => {return btn.btnPositionNo  === idx});
                if(btn && !btn.onClick) {
                    btn.onClick = () => {};
                }
                btnList.push(btn ? btn : {btnPositionNo: -1, btnName: '', btnBackGroundColor: '', btnTextColor: '', onClick: () => {}});
            }
            console.log(btnList);
            self.buttonSettings(btnList);
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
}

ko.components.register('stamp-layout-button', {
    viewModel: nts.uk.at.view.kdp.share.StampButtonLayOut, template: `
    <div data-bind="visible: buttonSettings().length > 0">
        
        <div data-bind="visible: buttonLayoutType() != 1">
            <div class="btn-grid-container cf" data-bind="foreach: buttonSettings">
                <div class="stamp-rec-btn-container pull-left">
                        <button class="stamp-rec-btn" id=""
                            data-bind="text: btnName, style:{ 'background-color' :  btnBackGroundColor, color :  btnTextColor }, click: function(data, event) { onClick($parent.parentVM) }, visible: btnPositionNo != -1"></button>
                </div>
            </div>
        </div>

        <div data-bind="visible: buttonLayoutType() == 1">
            <div class="btn-grid-container cf" data-bind="foreach: buttonSettings">
                <div class="stamp-square-btn-container pull-left">
                        <button class="stamp-rec-btn" id=""
                            data-bind="text: btnName, style:{ 'background-color' :  btnBackGroundColor, color :  btnTextColor }, click: function(data, event) { onClick($parent.parentVM) }, visible: btnPositionNo != -1"></button>
                </div>
            </div>
        </div>
        
    </div>
`});




