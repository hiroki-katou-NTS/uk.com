<template>
<div class="cmms45shrcomponentsapp1">
    <!--B1_1-->
    <div class="card card-label" v-if="true">
        <div class="card-header uk-bg-accordion mt-2 headerDiv">
            <span class="textPosition">{{ "KAFS06_1" | i18n }}</span>
        </div>
    </div>
    <!--B1_2-->
    <span class="text-left">{{ "KAFS06_1000" | i18n }}</span>

    <!--B2_1-->
    <div class="card card-label" v-if="true">
        <div class="card-header uk-bg-accordion mt-2 headerDiv">
            <span class="textPosition">{{ (c2 ? "KAFS06_12" : "KAFS06_14") | i18n }}</span>
        </div>
    </div>

    <div v-if="true">
        <!-- B2_2 -->
        <div class="row mt-1 mb-1">
            <div class="col-12 pl-3 textSize">{{"KAFS06_12" | i18n}}</div>
        </div>
        <!-- B2_3 -->
        <div class="row mt-1 mb-1">
            <div class="col-1.5 pr-2 pl-3">
                {{_.get(workInfo, 'workType.code') || ''}}
            </div>
            <div class="col pl-0">
                {{_.get(workInfo, 'workType.name') || ''}}
            </div>
        </div>

        <!-- B2_4 -->
        <div v-if="c9" class="row mt-1 mb-1">
            <div class="col-12 pl-3 textSize"> {{"KAFS06_13" | i18n}} </div>
        </div>
        <!-- B2_5 -->
        <div v-if="c9" class="row mt-1 mb-1">
            <div class="col-1.5 pr-2 pl-3">
                {{_.get(workInfo, 'workTime.code') || ''}}
            </div>
            <div class="col pl-0">
                {{_.get(workInfo, 'workTime.name') || ''}}
            </div>
        </div>
    </div>


    <!-- B3_1 -->
    <div class="card card-label" v-if="c9">
        <div class="card-header uk-bg-accordion mt-2 headerDiv">
            <span class="textPosition">{{ "KAFS06_15" | i18n }}</span>
        </div>
    </div>
    <!-- B3_2 -->
    <div v-if="c9">
        <div class="row mt-1 mb-1">
            <div class="col-1.5 pl-3 pr-1 text-left">
                {{workHours1.start || ''}}
            </div>
            <div v-show="workHours1.start != '' && workHours1.end != ''" class="col-0 pl-0 pr-0 text-center">
                ～
            </div>
            <div class="col-9 text-left pl-1">
                 {{workHours1.end || ''}}
            </div>
        </div>
    </div>


    <!-- B4_1 -->
    <div class="card card-label" v-if="c10">
        <div class="card-header uk-bg-accordion mt-2 headerDiv">
            <span class="textPosition">{{ "KAFS06_16" | i18n }}</span>
        </div>
    </div>
    <!-- B4_2 -->
    <div v-if="c10">
        <div class="row mt-1 mb-1">
            <div class="col-1.5 pl-3 pr-1 text-left">
                {{workHours2.start}}
            </div>
            <div v-show="workHours2.start != '' && workHours2.end != ''" class="col-0 pl-0 pr-0 text-center">
                ～
            </div>
            <div class="col-9 text-left pl-1">
                {{workHours2.end}}
            </div>
        </div>
    </div>

    
</div>
</template>