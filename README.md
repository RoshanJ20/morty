# Rick and Morty Explorer

Welcome to **RickAndMortyExplorer**, an Android application built using the MVVM architecture pattern in Kotlin. This app interfaces with the [Rick and Morty API](https://rickandmortyapi.com/) to provide a delightful experience exploring characters, locations, and episodes from the popular animated series.

## Features

- **Character Browser**: View detailed profiles of all your favorite characters.
- **Episode Guide**: Explore episodes with in-depth information.
- **Location Explorer**: Discover the various locations featured in the series.
- **Search Functionality**: Easily find characters, episodes, or locations.
- **Offline Support**: Data caching for offline viewing.


## Architecture

This project follows the MVVM (Model-View-ViewModel) architecture pattern, ensuring a clean separation of concerns and improved testability. Key components include:

- **Model**: Data classes representing API responses.
- **ViewModel**: Handles data processing and business logic.
- **View**: Activities and Fragments that display data to the user.

## Tech Stack

- **Kotlin**: Modern, expressive, and concise language for Android development.
- **Retrofit**: For making network requests to the Rick and Morty API.
- **Room**: Local database for caching data.
- **Coroutines**: Simplifies asynchronous programming.
- **LiveData**: Observes data changes and updates UI accordingly.
- **Data Binding**: Binds UI components to data sources.

## Getting Started

### Prerequisites

- Android Studio Bumblebee or later
- Android SDK 21 or higher
- Internet connection for API access

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/RoshanJ20/morty.git

2. **Open in Android Studio**:
   - Open Android Studio.
   - Select "Open an existing Android Studio project".
   - Navigate to the cloned repository and select it.

3. **Build the project**:
   - Click on "Build" in the top menu.
   - Select "Make Project".

4. **Run the app**:
   - Connect an Android device or start an emulator.
   - Click on "Run" in the top menu and select the device.

## Usage

- Launch the app to start exploring the world of Rick and Morty.
- Use the navigation drawer to switch between characters, episodes, and locations.
- Tap on any item to view detailed information.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any improvements or bug fixes.

1. Fork the project.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a pull request.

## Acknowledgments

- Thanks to [Rick and Morty API](https://rickandmortyapi.com/) for providing the data.
- Inspired by the amazing community of Android developers.

---

Feel free to reach out if you have any questions or suggestions!
