import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'kafs12-outing',
    template: require('./index.vue'),
    components: {},
    validations: {
        params: {
            timeZone: {
                timeRange: true,
                selectCheck: {
                    test(value: any) {
                        return (!!value.start && !!value.end) || (!value.start && !value.end);
                    },
                    messageId: 'MsgB_30'
                },
                constraint: 'AttendanceClock'
            }
        }
    },
    constraints: [
        'nts.uk.shr.com.time.AttendanceClock'
    ]
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
