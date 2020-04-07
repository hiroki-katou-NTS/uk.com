module nts.uk.at.view.kdp.share {
    export class StampButtonLayOut {
        buttonSettings: KnockoutObservableArray<ButtonSetting> = ko.observableArray([]);
        buttonLayoutType: KnockoutObservable<number>;
        
        constructor(params: any) {
            let self = this;
            console.log(params.data());
            if(params.data()) {
                self.buttonSettings(params.data().buttonSettings);
            };
        }

        public getButtonSetting(btnPos: number) {
            let self = this;
            return _.find(self.buttonSettings(), (btn) => {return btn.btnPositionNo  === btnPos});
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
        <div class="btn-grid-container" data-bind="foreach: buttonSettings()">
                <button class="stamp-rec-btn" id=""
                    data-bind="text: $data.buttonName, style:{ 'background-color' :  $data.buttonColor, color :  $data.textColor }"></button>
        </div>
    </com:ko-if>
`});




