import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import {TimePoint} from '@app/utils/time';

@component({
    name: 'kafs12-outing',
    template: require('./index.vue'),
    components: {},
    validations: {
        params: {
            timeZone: {
                timeRange: true,
                valueCheck: {
                    test(value: any) {
                        return (!value.start || (value.start >= 0 && value.start <= 1439))
                            && (!value.end || (value.end >= 0 && value.end <= 1439));
                    },
                    messageId: ['MsgB_45', TimePoint.toString(0, 'h'), TimePoint.toString(1439, 'h')]
                },
                requiredCheck: {
                    test(value: any) {
                        return (!!value.start && !!value.end) || (!value.start && !value.end);
                    },
                    messageId: ['MsgB_30']
                },
            }
        }
    },
    constraints: []
})
export class KafS12OutingComponent extends Vue {
    @Prop({ default: null })
    public readonly params: any;
    @Prop({ default: false })
    public readonly displaySwitch: boolean;
    @Prop({ default: [] })
    public readonly switchOptions: Array<any>;
    @Prop({ default: null })
    public appDispInfoStartupOutput: any;

    public created() {
        const self = this;
    }
}
