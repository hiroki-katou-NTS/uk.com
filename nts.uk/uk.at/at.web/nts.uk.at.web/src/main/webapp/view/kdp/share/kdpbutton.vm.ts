module nts.uk.at.view.kdp.share {
    const layoutType = {
        LARGE_2_SMALL_4: 0,
        SMALL_8:1
    }
    export class StampButtonLayOut {
        buttonSettings: KnockoutObservableArray<ButtonSetting> = ko.observableArray([]);
        buttonLayoutType: KnockoutObservable<number>;
        
        constructor(params: any) {
            let self = this;
            if(params.data()) {
                console.log(params.data().buttonSettings);
                self.buttonSettings(params.data().buttonSettings);
            };
        }

        public getButtonSetting(btnPos: number) {
            let self = this;
            return _.find(self.buttonSettings(), (btn) => {return btn.btnPositionNo  === btnPos});
        }

        public correntBtnSetting(btnSettings: Array<ButtonSetting>) {
            let self = this;
            if(self.buttonLayoutType() === layoutType.LARGE_2_SMALL_4) {
                for (let idx = 1; idx < 6; idx++) {
                    // const element = array[idx];
                    
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
}

ko.components.register('stamp-layout-button', {
    viewModel: nts.uk.at.view.kdp.share.StampButtonLayOut, template: `
    <com:ko-if bind="buttonSettings().length > 0">
        <div class="btn-grid-container" data-bind="foreach: buttonSettings">
                <button class="stamp-rec-btn" id=""
                    data-bind="text: btnName, style:{ 'background-color' :  btnBackGroundColor, color :  btnTextColor }"></button>
        </div>
    </com:ko-if>
`});




