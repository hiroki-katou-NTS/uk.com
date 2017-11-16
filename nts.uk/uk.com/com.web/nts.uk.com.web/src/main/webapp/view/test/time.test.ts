/// <reference path="../nts.uk.com.web.nittsu.bundles.d.ts"/>
/// <reference path="testutil.ts"/>


module kiban.test {
    
    import mbt = nts.uk.time.minutesBased;
    
    module parse {
        module durationString {
            console.log('parse.duration');
            
            let t = mbt.parse.durationString;
            
            assert('10:00').that(t('10:00'), is(600));
            
            assert('0').that(t('0'), is(0));
            
            assert('-1').that(t('-1'), is(-1));
            
            assert('59').that(t('59'), is(59));
            assert('60 is NaN').that(t('60'), isNotANumber());
            
            assert('1:00').that(t('1:00'), is(60));
            assert('0:59').that(t('0:59'), is(59));
            
            assert('100').that(t('100'), is(60));
            
            assert('-100').that(t('-100'), is(-60));
        }
    }
    
}