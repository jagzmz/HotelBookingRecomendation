# Hotel Booking & Recommendations

## Workflow
<img src='./hotelapp.gif'>

## Problem Statement
1.  Take a sample JSON for a list of Hotels
2.  The hotel shall have most basic identifying fields
3.  Create a few users who will perform the following activities.
4.  Track the visitors on a hotel page
6.  Users make a Draft Booking, where the user tries to book a hotel but don't complete the process
7.  Users book a Hotel i.e. Create a completed booking
8.  Display the activities happening around hotel page (Visits, Draft Bookings, Completed Booking)
9.  Display recommendations of other hotels based on the activities done by the user.
10.  Implement a basic UI with minimal functionality required.

## App Features
1. Shows list of hotes as structured in [HotelMock.json](https://github.com/jagzmz/HotelBookingRecomendation/blob/master/app/src/main/assets/hotels.json)
2. List of hotels shown with their ratings, tags and views.
3. On clicking "Book"  user can create either Draft or Complete Booking.
4. Activities including Views, Drafts, Bookings are displayed to all. 

## Recommendation System
Each hotel has tags attached with it. Upon successful booking of the user,
He/she can navigate to the "**Recommendation**" tab to see relevant hotels having similar tags.

