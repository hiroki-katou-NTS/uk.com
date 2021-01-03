<template>
<div class="kafs12a">
    <step-wizard v-bind:items="['KAFS12_1','KAFS12_2','KAFS12_3']" v-bind:selected="step"/>
    <kaf-s12-a1 
    v-if="step == 'KAFS12_1'"
    v-bind:mode="mode"
    @next-to-step-two="handleNextToStepTwo"
    />
    <kaf-s12-a2 
    v-if="step == 'KAFS12_2'"
    v-bind:mode="mode"
    v-bind:timeLeaveAppDispInfo="timeLeaveAppDispInfo"
    @next-to-step-three="handleNextToStepThree" 
    />
    <kaf-s12-c
    v-bind:mode="mode" 
    v-if="step == 'KAFS12_3'" />
</div>
</template>