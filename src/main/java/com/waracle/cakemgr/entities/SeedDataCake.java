package com.waracle.cakemgr.entities;

import java.net.URI;

/**
 * Models the Cake seed data entities, containing the following attributes :-
 *
 * - title - the title pf the Cake.
 * - desc - the description of the Cake.
 * - image - the Cake image URI in String format.
 */
public record SeedDataCake(String title, String desc, String image) {
}
