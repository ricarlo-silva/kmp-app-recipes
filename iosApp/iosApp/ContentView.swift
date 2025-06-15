import SwiftUI
import shared

// struct ContentView: View {
// 	let greet = Greeting().greet()
//
// 	var body: some View {
// 		Text(greet)
// 	}
// }
//
// struct ContentView_Previews: PreviewProvider {
// 	static var previews: some View {
// 		ContentView()
// 	}
// }

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(edges: .all)
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}
