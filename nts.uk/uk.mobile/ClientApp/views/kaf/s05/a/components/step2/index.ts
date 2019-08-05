import { _, Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { Kafs05Model } from '../common/CommonClass';

@component({
    name: 'kafS05a2',
    template: require('./index.html'),
    resource: require('../../resources.json'),
    validations: {
        kafs05ModelStep2: {
            overtimeHours: {
                check: {
                    test(value: any) {
                        if (this.kafs05ModelStep2.enableOvertimeInput) {
                            for (let i of value) {
                                if (this.kafs05ModelStep2.prePostSelected == 0 && i.frameNo == 12) {
                                    continue;
                                }
                                if (!_.isNil(i.applicationTime)) {
                                    return true;
                                }
                            }

                            return false;
                        }

                        return true;
                    },
                    messageId: 'Msg_1563'
                }
            },
            selectedReason: {
                checkNull: {
                    test(value: any) {
                        if (this.kafs05ModelStep2.requiredReason) {
                            if ((value + this.kafs05ModelStep2.multilContent).length == 0) {
                                return false;
                            }

                            return true;
                        }

                        return true;
                    },
                    messageId: 'Msg_115'
                },
                checkMaxLength: {
                    test(value: any) {
                        let comboBoxReason: string = this.getComboBoxReason(value, this.kafs05ModelStep2.reasonCombo, this.kafs05ModelStep2.typicalReasonDisplayFlg);
                        if (this.countHalf(comboBoxReason + '\n' + this.kafs05ModelStep2.multilContent) > 400) {
                            return false;
                        }

                        return true;
                    },
                    messageId: 'Msg_960'
                }
            },
            multilContent: {
                checkNull: {
                    test(value: any) {
                        if (this.kafs05ModelStep2.requiredReason) {
                            if ((value + this.kafs05ModelStep2.selectedReason).length == 0) {
                                return false;
                            }

                            return true;
                        }

                        return true;
                    },
                    messageId: 'Msg_115'
                },
                checkMaxLength: {
                    test(value: any) {
                        let comboBoxReason: string = this.getComboBoxReason(this.kafs05ModelStep2.selectedReason, this.kafs05ModelStep2.reasonCombo, this.kafs05ModelStep2.typicalReasonDisplayFlg);
                        if (this.countHalf(comboBoxReason + '\n' + value) > 400) {
                            return false;
                        }

                        return true;
                    },
                    messageId: 'Msg_960'
                }
            },
            selectedReason2: {
                checkMaxLength: {
                    test(value: any) {
                        let comboDivergenceReason = this.getComboBoxReason(value, this.kafs05ModelStep2.reasonCombo2, this.kafs05ModelStep2.displayDivergenceReasonForm);
                        if (this.countHalf(comboDivergenceReason + '\n' + this.kafs05ModelStep2.multilContent2) > 400) {
                            return false;
                        }

                        return true;
                    },
                    messageId: 'Msg_960'
                }
            },
            multilContent2: {
                checkMaxLength: {
                    test(value: any) {
                        let comboDivergenceReason = this.getComboBoxReason(this.kafs05ModelStep2.selectedReason2, this.kafs05ModelStep2.reasonCombo2, this.kafs05ModelStep2.displayDivergenceReasonForm);
                        if (this.countHalf(comboDivergenceReason + '\n' + value) > 400) {
                            return false;
                        }

                        return true;
                    },
                    messageId: 'Msg_960'
                }
            }
        },
    }
})
export class KafS05aStep2Component extends Vue {
    @Prop()
    public kafs05ModelStep2: Kafs05Model;

    @Watch('kafs05ModelStep2.selectedReason')
    public validateSelectedReason() {
        this.$validate();
    }

    @Watch('kafs05ModelStep2.multilContent')
    public validateMultilContent() {
        this.$validate();
    }

    @Watch('kafs05ModelStep2.selectedReason2')
    public validateSelectedReason2() {
        this.$validate();
    }

    @Watch('kafs05ModelStep2.multilContent2')
    public validateMultilContent2() {
        this.$validate();
    }

    @Watch('kafs05ModelStep2.overtimeHours', { deep: true, immediate: false })
    public validateOvertimeHours(value: any, oldValue: any) {
        this.$validate();
    }

    public created() {
        console.log(this.kafs05ModelStep2);
    }

    public next() {
        let self = this.kafs05ModelStep2;

        this.$validate();
        if (!this.$valid) {
            window.scrollTo({ top: 0, behavior: 'smooth' });

            return;
        }

        this.$emit('toStep3', this.kafs05ModelStep2);
    }

    public getComboBoxReason(selectID: string, listID: Array<any>, displaySet: boolean): string {
        if (!displaySet || _.isNil(selectID) || selectID == '') {
            return '';
        }
        let reasonValue = _.find(listID, (o) => o.reasonId == selectID).reasonName;
        if (_.isNil(reasonValue)) {
            return '';
        }

        return reasonValue;
    }

    public countHalf(text: string) {
        let count = 0;
        for (let i = 0; i < text.length; i++) {
            let c = text.charCodeAt(i);

            // 0x20 ～ 0x80: 半角記号と半角英数字
            // 0xff61 ～ 0xff9f: 半角カタカナ
            if ((0x20 <= c && c <= 0x7e) || (0xff61 <= c && c <= 0xff9f)) {
                count += 1;
            } else {
                count += 2;
            }
        }

        return count;
    }
}