<template>
<div class="cmms45componentsapp1">
    <div class="row" v-if="$app"> 
      <div class="col-12">
        <div class="row mt-1 pl-2 pt-1 pb-1 uk-bg-headline border-top uk-border-light-gray">{{'CMMS45_21' | i18n}}</div>
        <div class="row pl-2 pt-1 pb-1 border-top uk-border-light-gray">
            {{ $app.applicant | i18n }} 
            <span v-if="$app.representer">{{ 'CMMS45_22' | i18n($app.representer) }}</span></div>
        <div class="row pl-2 pt-1 pb-1 uk-bg-headline border-top uk-border-light-gray">{{'CMMS45_23' | i18n}}</div>
        <div class="row pl-2 pt-1 pb-1 border-top uk-border-light-gray">
            <div class="col-12">
                <div class="row">{{ $app.appDate | i18n }} {{ appType }} {{'CMMS45_24' | i18n(prePost)}}</div>
                <div class="row">{{'CMMS45_25' | i18n($app.inputDate)}}</div>
            </div>
        </div>
        <div class="row pl-2 pt-1 pb-1 uk-bg-headline border-top uk-border-light-gray" v-if="$app.displayCaculationTime">{{'CMMS45_26' | i18n}}</div>
        <div class="row pl-2 pt-1 pb-1 border-top uk-border-light-gray" v-if="$app.displayCaculationTime">
            <div class="col-12">
                <div class="row">{{'CMMS45_81' | i18n}}</div>
                <div class="row ml-n5 pl-3" v-if="$app.workTypeCD">
                    <div class="col-1 mr-2">{{ $app.workTypeCD | i18n }}</div>
                    <div class="col-10">{{ ($app.workTypeName || 'CMMS45_87') | i18n }}</div>
                </div>
                <div class="row ml-n5 pl-3" v-if="!$app.workTypeCD">
                    <div class="col">{{ 'CMMS45_15' | i18n }}</div>
                </div>
                <div class="row">{{'CMMS45_82' | i18n}}</div>
                <div class="row ml-n5 pl-3" v-if="$app.workTimeCD">
                    <div class="col-1 mr-2">{{ $app.workTimeCD | i18n }}</div>
                    <div class="col-10">{{ ($app.workTimeName || 'CMMS45_87') | i18n }}</div>
                </div>
                <div class="row ml-n5 pl-3" v-if="!$app.workTimeCD">
                    <div class="col">{{ 'CMMS45_15' | i18n }}</div>
                </div>
            </div>
        </div>
        <div class="row pl-2 pt-1 pb-1 uk-bg-headline border-top uk-border-light-gray" v-if="$app.displayCaculationTime">{{'CMMS45_83' | i18n}}</div>
        <div class="row pl-2 pt-1 pb-1 border-top uk-border-light-gray" v-if="$app.displayCaculationTime">
            <div class="col-12">
                <div class="row">
                    <span v-if="$app.startTime">{{ $app.startTime | timewd}} {{ 'CMMS45_12' | i18n}} {{ $app.endTime | timewd}}</span>  
                    <span v-else>{{'CMMS45_15' | i18n}} {{ 'CMMS45_12' | i18n}}</span>
                </div>
            </div>
        </div>
        <div class="row pl-2 pt-1 pb-1 uk-bg-headline border-top uk-border-light-gray"
            v-if="$app.displayCaculationTime && $app.displayRestTime">{{'CMMS45_27' | i18n}}</div>
        <div class="row" v-if="$app.displayCaculationTime && $app.displayRestTime">
            <div class="col-12">
                <div class="row pl-2 pt-1 pb-1 border-top uk-border-light-gray" v-for="(breakTime, breakTimeIndex) in [1,2,3,4,5,6,7,8,9,10]" v-bind:key="breakTimeIndex">
                    <div class="col-12">
                        <div class="row">{{'CMMS45_85' | i18n(breakTime.toString())}}</div>
                        <div class="row" v-if="breakTimeLst[breakTimeIndex]">
                            <span>{{ breakTimeLst[breakTimeIndex].startTime | timewd}} {{ 'CMMS45_12' | i18n}} {{ breakTimeLst[breakTimeIndex].endTime | timewd}}</span>
                        </div>
                        <div class="row" v-else>{{'CMMS45_15' | i18n}}</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row pl-2 pt-1 pb-1 uk-bg-headline border-top uk-border-light-gray">{{'CMMS45_86' | i18n}}</div>
        <div class="row">
            <div class="col-12">
                <div class="row pl-2 pt-1 pb-1 border-top uk-border-light-gray" v-for="(overTime, overTimeIndex) in overTimeLst" 
                    v-bind:key="overTimeIndex" v-show="overTime.getDisplay">
                    <div class="col-12" v-if="overTime.frameNo <= 10">
                        <div class="row">
                            <div class="col-6">{{ overTime.frameName | i18n }}</div>
                            <div class="col-6">{{ (overTime.appTime || 0) | timept }}</div>
                        </div>
                        <div class="row" v-if="isPostApp">
                            <div class="col-6 pl-4">
                                <span class="far fa-clock display-time">事前[{{ (overTime.preAppTime || 0) | timept }}]</span>
                            </div>
                            <div class="col-6">
                                <span class="far fa-clock display-time">実績[{{ (overTime.actualTime || 0) | timept }}]</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-12" v-if="overTime.frameNo == 11 && $app.appOvertimeNightFlg == 1">
                        <div class="row">
                            <div class="col-6">{{ 'CMMS45_30' | i18n }}</div>
                            <div class="col-6">{{ (overTime.appTime || 0) | timept }}</div>
                        </div>
                        <div class="row" v-if="isPostApp">
                            <div class="col-6 pl-4">
                                <span class="far fa-clock display-time">事前[{{ (overTime.preAppTime || 0) | timept }}]</span>
                            </div>
                            <div class="col-6">
                                <span class="far fa-clock display-time">実績[{{ (overTime.actualTime || 0) | timept }}]</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-12" v-if="overTime.frameNo == 12 && $app.flexFLag">
                        <div class="row">
                            <div class="col-6">{{ 'CMMS45_31' | i18n }}</div>
                            <div class="col-6">{{ (overTime.appTime || 0) | timept }}</div>
                        </div>
                        <div class="row" v-if="isPostApp">
                            <div class="col-6 pl-4">
                                <span class="far fa-clock display-time">事前[{{ (overTime.preAppTime || 0) | timept }}]</span>
                            </div>
                            <div class="col-6">
                                <span class="far fa-clock display-time">実績[{{ (overTime.actualTime || 0) | timept }}]</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row pl-2 pt-1 pb-1 uk-bg-headline border-top uk-border-light-gray" v-if="false">{{'CMMS45_32' | i18n}}</div>
        <div class="row pl-2 pt-1 pb-1 border-top uk-border-light-gray" v-if="false">
            <div class="col-12">
                <div class="row border-top uk-border-light-gray" v-for="(restTime, restTimeIndex) in restTimeLst" v-bind:key="restTimeIndex">
                    <div class="col-12">
                        <div class="row">
                            <div class="col-4">{{ restTime.frameName | i18n }}</div>
                            <div class="col-8">{{ (restTime.appTime || 0) | timept }}</div>
                        </div>
                        <div class="row">
                            <div class="col-4 pl-4">
                                <span class="far fa-clock display-time">事前[{{ (restTime.preAppTime || 0) | timept }}]</span>
                            </div>
                            <div class="col-8">
                                <span class="far fa-clock display-time">実績[{{ (restTime.actualTime || 0) | timept }}]</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row pl-2 pt-1 pb-1 uk-bg-headline border-top uk-border-light-gray" v-if="$app.displayBonusTime">{{'CMMS45_33' | i18n}}</div>
        <div class="row" v-if="$app.displayBonusTime">
            <div class="col-12">
                <div class="row pl-2 pt-1 pb-1 border-top uk-border-light-gray" v-for="(payTime, payTimeIndex) in payTimeLst" v-bind:key="payTimeIndex">
                    <div class="col-12">
                        <div class="row">
                            <div class="col-5">{{ payTime.frameName | i18n }}</div>
                            <div class="col-2 pl-0">{{ (payTime.appTime || 0) | timept }}</div>
                            <div class="col-5 pl-3" v-if="isPostApp">
                                <span class="far fa-clock display-time">事前[{{ (payTime.preAppTime || 0) | timept }}]</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row pl-2 pt-1 pb-1 uk-bg-headline border-top uk-border-light-gray"
            v-if="$app.displayAppReasonContentFlg || $app.typicalReasonDisplayFlg">{{'CMMS45_34' | i18n}}</div>
        <div class="row pl-2 pt-1 pb-1 border-top uk-border-light-gray"
            v-if="$app.displayAppReasonContentFlg || $app.typicalReasonDisplayFlg">{{ $app.appReason }}</div>
        <div class="row pl-1 pt-1 pb-1 uk-bg-headline border-top uk-border-light-gray"
            v-if="$app.prePostAtr==1 && $app.displayDivergenceReasonForm && $app.displayDivergenceReasonInput">
            {{'CMMS45_35' | i18n}}
        </div>
        <div class="row pl-2 pt-1 pb-1 border-top uk-border-light-gray"
            v-if="$app.prePostAtr==1 && $app.displayDivergenceReasonForm && $app.displayDivergenceReasonInput">
            {{ $app.divergenceReasonContent }}
        </div>
      </div>
    </div>
</div>
</template>