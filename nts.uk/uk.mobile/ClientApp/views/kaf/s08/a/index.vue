<template>
<div class="kafs08a mt-n2">
    <div class="step-wizard">
      <step-wizard v-bind:items="['KAFS08_10', 'KAFS08_11', 'KAFS08_12']" v-bind:selected="step" />
    </div>
    <kafs08a1 @nextToStepTwo="ProcessNextToStepTwo" v-if="step == 'KAFS08_10'" />
    <kafs08a2 @prevStepOne="ProcessPrevStepOne" @nextToStepThree="ProcessNextToStepThree" :derpartureTime="departureTime" v-bind:comment="comment" :returnTime="returnTime" v-bind:table="table" v-if="step == 'KAFS08_11'" />
    <kafs08c v-if="step =='KAFS08_12'" />
</div>
</template>