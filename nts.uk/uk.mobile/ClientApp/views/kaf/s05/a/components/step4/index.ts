import { _, Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { storage } from '@app/utils';
import { CmmS45CComponent } from '../../../../../cmm/s45/c';
import { Kafs05Model } from '../common/CommonClass';

@component({
    name: 'KafS05a4',
    template: require('./index.html'),
    resource: require('../../resources.json'),
    components: {
        'cmms45c': CmmS45CComponent
    },
})
export class KafS05aStep4Component extends Vue {

    @Prop()
    public kafs05ModelStep4: Kafs05Model;

    public toRegisterList() {
        storage.local.removeItem('CMMS45_AppListExtractCondition');
        this.$goto('cmms45a', {CMMS45_FromMenu: false});
    }

    public toConfirmList() {
        storage.local.removeItem('CMMS45_AppListExtractCondition');
        this.$modal('cmms45c', { 'listAppMeta': [this.kafs05ModelStep4.appID], 'currentApp': this.kafs05ModelStep4.appID });
    }
}